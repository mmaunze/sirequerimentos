
document.getElementById('j-pro').addEventListener('submit', function(event) {
    event.preventDefault(); // Impede o envio do formulário padrão
  
    // Extrai os dados do formulário
    var idmaterial = document.getElementById('idmaterial').value;
    var anoPublicacao = document.getElementById('anoPublicacao').value;
    var titulo = document.getElementById('titulo').value;
    var autor1 = document.getElementById('autor1').value;
    var autor2 = document.getElementById('autor2').value;
    var ISBN = document.getElementById('ISBN').value;
    var idarea = document.getElementById('idarea').value;
    var localidade = document.getElementById('local').value;
    var editora = document.getElementById('editora').value;
    var volume = document.getElementById('volume').value;
    var edicao = document.getElementById('edicao').value;
    var nrPaginas = document.getElementById('nrPaginas').value;
    var idioma = document.getElementById('idioma').value;
    var mensagem = document.getElementsByName('message')[0].value;
  
    // Cria um objeto com os dados a serem enviados
    var data = {
      idmaterial: idmaterial,
      anoPublicacao: anoPublicacao,
      titulo: titulo,
      autor1: autor1,
      autor2: autor2,
      ISBN: ISBN,
      idarea: idarea,
      localidade: localidade,
      editora: editora,
      volume: volume,
      edicao: edicao,
      nrPaginas: nrPaginas,
      idioma: idioma,
      message: mensagem
    };
  
    // Envia os dados ao servidor usando AJAX
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '../Materiais', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onreadystatechange = function() {
      if (xhr.readyState === 4 && xhr.status === 200) {
        // Aqui você pode lidar com a resposta do servidor
        console.log(xhr.responseText);
      }
    };
    xhr.send(JSON.stringify(data));
  });
  