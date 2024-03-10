$(document).ready(function(){

    //  masking
    $('#idmaterial').mask('9999', {placeholder:'x'});
    $('#idutilizador').mask('9999999999', {placeholder:'x'});
    $('#anoPublicacao').mask('9999', {placeholder:'x'});
  
    /***************************************/
    /* Datepicker */
    /***************************************/
    // Validation
    // idMaterial, , , , , , , , , 
    $( "#j-pro" ).justFormsPro({
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
        idArea: {
          required: true
        }
        ,
        localizacao: {
          required: false
        },
        estado: {
          required: false
        }
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
        idArea: {
          required: "informar a area cientifica"
        }
        ,
        localizacao: {
          required: "Informar a localizacao"
        }
        ,
        estado: {
          required: "informar o estado"
        }
      },
      formType: {
        multistep: true
      },
      afterSubmitHandler: function() {
        return true;
      }
    });
  
  
  });