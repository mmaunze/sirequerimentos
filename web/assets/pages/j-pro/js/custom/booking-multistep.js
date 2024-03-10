$(document).ready(function(){

  //  masking
  $('#idmaterial').mask('9999', {placeholder:'x'});
  $('#idutilizador').mask('9999999999', {placeholder:'x'});
  $('#anoPublicacao').mask('9999', {placeholder:'x'});
  $('#valor').mask('999', {placeholder:'x'});
  $('#ISBN').mask('999-9-99-999999-9', {placeholder:'x'});


  /***************************************/
  /* Datepicker */
  /***************************************/
  // Validation
  // idMaterial, , , , , , , , , 
  $("#j-pro").justFormsPro({
    rules: {
      idmaterial: {
        required: true
      },
      autor: {
        required: true,
        
      },
      titulo: {
        required: true
      },
      anoPublicacao: {
        required: true,
        integer: true,
        minvalue: 1200,
        maxvalue: 2023
      },
      idioma: {
        required: true,
      },
      nrPaginas: {
        required: true,
        integer: true
      },
      idTipomaterial: {
        required: true
      },
      idarea: {
        required: false,
      }
      ,
      localizacao: {
        required: false
      },
      estado: {
        required: false
      },
      ISBN:{
        required: true
      },
      editora:{
        required: true
      },
      localidade:{
        required: true
      },
      valor:{
        required: true
      },
    },
/// idMaterial, , , , , , , , , 
    messages: {
      idmaterial: {
        required: "informar o numero de registo"
      },
      autor: {
        required: "informar o nome do Autor",
      },
      titulo: {
        required: "informar o titulo do material"
      },

      anoPublicacao: {
        required: "informar o ano de publicacao",
        integer: "apenas numeros permitdos",
        minvalue: "Ano invalido",
        maxvalue:  "Inserior um ano valido"
      },
      idioma: {
        required: "Informar o idioma",
      },
      nrPaginas: {
        required: "informar o numero de paginas"
      },
      idTipomaterial: {
        required: "informar o tipo de material"
      },
      idarea: {
        required: "informar a area cientifica",
      }
      ,
      localizacao: {
        required: "Informar a localizacao"
      }
      ,
      estado: {
        required: "informar o estado"
      },
      ISBN:{
        required: "Informar o codigo ISBN do livro"
      },
      editora:{
        required: "Informar a editora do livro"
      },
      localidade:{
        required: "Informar o local da publicacao do livro"
      },
      valor:{
        required: "Informar o valor da multa"
      },
    },
    formType: {
      multistep: true
    },
    afterSubmitHandler: function() {
      // Executar ação adicional aqui
      console.log('Formulário enviado com sucesso!');
      // Retornar true para permitir que o envio do formulário continue normalmente
      return true;
    }
    
  });

  $("#idTipomaterial").attr("data-validation", "required");
  $("#idarea").attr("data-validation", "required");


});