
function getPageList(totalPages, page, maxLength) {
  function range(start, end) {
    return Array.from(Array(end - start + 1), (_, i) => i + start);
  }

  var sideWidth = maxLength < 4 ? 1 : 2;
  var leftWidth = (maxLength - sideWidth * 2 - 3) >> 1;
  var rightWidth = (maxLength - sideWidth * 2 - 3) >> 1;

  if (totalPages <= maxLength) {
    return range(1, totalPages);
  }

  if (page <= maxLength - sideWidth - 1 - rightWidth) {
    return range(1, maxLength - sideWidth - 1).concat(0, range(totalPages - sideWidth + 1, totalPages));
  }

  if (page >= totalPages - sideWidth - 1 - rightWidth) {
    return range(1, sideWidth).concat(0, range(totalPages - sideWidth - 1 - rightWidth - leftWidth, totalPages));
  }

  return range(1, sideWidth).concat(0, range(page - leftWidth, page + rightWidth), 0, range(totalPages - sideWidth + 1, totalPages));
}

$(function () {
  var numberOfItems = $(".obra-card-container .obra-card").length;
  var limitPerPage = 3; //How many card items visible per a page
  var totalPages = Math.ceil(numberOfItems / limitPerPage);
  var paginationSize = 7; //How many page elements visible in the pagination
  var currentPage;

  function showPage(whichPage) {
    if (whichPage < 1 || whichPage > totalPages) return false;

    currentPage = whichPage;

    $(".obra-card-container .obra-card").hide().slice((currentPage - 1) * limitPerPage, currentPage * limitPerPage).show();

    $(".pagination li").slice(1, -1).remove();

    getPageList(totalPages, currentPage, paginationSize).forEach(item => {
      $("<li>").addClass("page-item").addClass(item ? "current-page" : "dots")
        .toggleClass("active", item === currentPage).append($("<a>").addClass("page-link")
          .attr({ href: "javascript:void(0)" }).text(item || "...")).insertBefore(".next-page");
    });

    $(".previous-page").toggleClass("disable", currentPage === 1);
    $(".next-page").toggleClass("disable", currentPage === totalPages);
    return true;
  }

  $(".pagination").append(
    $("<li>").addClass("page-item").addClass("previous-page").append($("<a>").addClass("page-link").attr({ href: "javascript:void(0)" }).text("Prev")),
    $("<li>").addClass("page-item").addClass("next-page").append($("<a>").addClass("page-link").attr({ href: "javascript:void(0)" }).text("Next"))
  );

  $(".obra-card-container").show();
  showPage(1);

  $(document).on("click", ".pagination li.current-page:not(.active)", function () {
    return showPage(+$(this).text());
  });

  $(".next-page").on("click", function () {
    return showPage(currentPage + 1);
  });

  $(".previous-page").on("click", function () {
    return showPage(currentPage - 1);
  });
});

const cardsPerPage = 4;
let currentPage = 1;

function showCards(page) {
  const startIndex = (page - 1) * cardsPerPage;

  const filteredCategory = $("button[data-filter].active").data("filter");
  const searchValue = $("#search").val().toLowerCase();

  $(".obra-card").each(function () {
    const cardCategory = $(this).data("category");
    const cardTitle = $(this).data("title").toLowerCase();

    if ((filteredCategory === "all" || cardCategory === filteredCategory) && (searchValue === "" || cardTitle.includes(searchValue))) {
      $(this).show();
    } else {
      $(this).hide();
    }
  });

  const endIndex = startIndex + cardsPerPage;
  const visibleCards = $(".obra-card:visible");
  const displayCards = visibleCards.slice(startIndex, endIndex);

  $(".obra-card").hide();
  displayCards.show();
}



$(document).on("click", "button[data-page]", function () {
  const targetPage = parseInt($(this).data("page"));
  if (targetPage !== currentPage) {
    currentPage = targetPage;
    showCards(currentPage);
    updatePagination();
  }
});

$(document).on("click", "button[data-filter]", function () {
  $("button[data-filter]").removeClass("active");
  $(this).addClass("active");
  currentPage = 1;
  showCards(currentPage);
  updatePagination();
});

$(document).on("keyup", "#search", function () {
  currentPage = 1;
  showCards(currentPage);
  updatePagination();
});

