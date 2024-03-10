// Aprovar
$(document).ready(function () {
    $("#aprovar-form").submit(function (event) {
        event.preventDefault();
        var formData = $(this).serialize();
        $.ajax({
            url: "pedidos",
            type: "post",
            data: formData,
            dataType: 'json',
            success: function (response) {
                // Verificar o resultado do processamento
                if (response.success) {
                    // Processamento bem-sucedido

                    new PNotify({
                        title: ' Sucesso ',
                        text: response.message,
                        type: 'success'
                    });

                    // Aguardar 3 segundos e, em seguida, atualizar a tabela
                    setTimeout(function () {
                        location.reload();
                    }, 3000);  // 3000 milissegundos = 3 segundos
                } else {
                    // Processamento com erro
                    new PNotify({
                        title: 'Erro ao processar o pedido',
                        text: response.error,
                        type: 'warning'
                    });

                    // Aguardar 3 segundos e, em seguida, atualizar a tabela
                    setTimeout(function () {
                        location.reload();
                    }, 3000);  // 3000 milissegundos = 3 segundos
                    // Lidar com o erro de acordo com suas necessidades
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                // Lidar com erros de requisição, se houver
                new PNotify({
                    title: 'Erro ao processar o pedido',
                    text: response.message,
                    type: 'error'
                });
            }
        });
    });
});

//Cancelar

$(document).ready(function () {
    $("#cancelar-form").submit(function (event) {
        event.preventDefault();
        var formData = $(this).serialize();
        $.ajax({
            url: "pedidos",
            type: "post",
            data: formData,
            dataType: 'json',
            success: function (response) {
                // Verificar o resultado do processamento
                if (response.success) {
                    // Processamento bem-sucedido

                    new PNotify({
                        title: ' Sucesso ',
                        text: response.message,
                        type: 'success'
                    });

                    // Aguardar 3 segundos e, em seguida, atualizar a tabela
                    setTimeout(function () {
                        location.reload();
                    }, 2500);  // 3000 milissegundos = 3 segundos
                } else {
                    // Processamento com erro
                    new PNotify({
                        title: 'Erro ao processar o pedido',
                        text: response.error,
                        type: 'warning'
                    });

                    // Aguardar 3 segundos e, em seguida, atualizar a tabela
                    setTimeout(function () {
                        location.reload();
                    }, 3000);  // 3000 milissegundos = 3 segundos
                    // Lidar com o erro de acordo com suas necessidades
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                // Lidar com erros de requisição, se houver
                new PNotify({
                    title: 'Erro ao processar o pedido',
                    text: response.message,
                    type: 'error'
                });
            }
        });
    });
});

//Rejeitar
$(document).ready(function () {
    $("#rejeitar-form").submit(function (event) {
        event.preventDefault();
        var formData = $(this).serialize();
        $.ajax({
            url: "pedidos",
            type: "post",
            data: formData,
            dataType: 'json',
            success: function (response) {
                // Verificar o resultado do processamento
                if (response.success) {
                    // Processamento bem-sucedido

                    new PNotify({
                        title: ' Sucesso ',
                        text: response.message,
                        type: 'success'
                    });

                    // Aguardar 3 segundos e, em seguida, atualizar a tabela
                    setTimeout(function () {
                        location.reload();
                    }, 3000);  // 3000 milissegundos = 3 segundos
                } else {
                    // Processamento com erro
                    new PNotify({
                        title: 'Erro ao processar o pedido',
                        text: response.error,
                        type: 'warning'
                    });

                    // Aguardar 3 segundos e, em seguida, atualizar a tabela
                    setTimeout(function () {
                        location.reload();
                    }, 3000);  // 3000 milissegundos = 3 segundos
                    // Lidar com o erro de acordo com suas necessidades
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                // Lidar com erros de requisição, se houver
                new PNotify({
                    title: 'Erro ao processar o pedido',
                    text: response.message,
                    type: 'error'
                });
            }
        });
    });
});

