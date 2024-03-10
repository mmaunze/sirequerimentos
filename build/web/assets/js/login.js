const form = document.querySelector("form");
usuario = form.querySelector(".username"),
        usuarioe = usuario.querySelector("input"),
        senha = form.querySelector(".senha"),
        senhae = senha.querySelector("input");

usuarioe.onkeyup = () => {
    validaUsuario();
}; 
senhae.onkeyup = () => {
    validaSenha();
}; 

form.addEventListener('submit', (event) => {
    validarFormulario();
    console.log(isFormValid());
    if (isFormValid() === false) {
        event.preventDefault();
    } else {
      
        form.submit();
    }

});


function validarFormulario() {
    form.onsubmit = (event) => {
        event.preventDefault();
        (usuarioe.value === "") ? usuario.classList.add("shake", "error") : validaUsuario();
        (senhae.value === "") ? senha.classList.add("shake", "error") : validaSenha();

        setTimeout(() => {
            usuario.classList.remove("shake");
            senha.classList.remove("shake");
        }, 500);
    };
}

function validaUsuario() {
    let pattern = /[a-z]$/; 
    if (!usuarioe.value.match(pattern)) { 
        usuario.classList.add("error");
        usuario.classList.remove("valid");
        let errorTxt = usuario.querySelector(".erro");
        (usuarioe.value !== "") ? errorTxt.innerText = "Caracter invalido" : errorTxt.innerText = "Username nao pode estar vazio";
    } else { 
        usuario.classList.remove("error");
        usuario.classList.add("valid");
    }
}

function validaSenha() { 
    if (senhae.value === "") { 
        senha.classList.add("error");
        senha.classList.remove("valid");
    } else { 
        senha.classList.remove("error");
        senha.classList.add("valid");
    }
}

function isFormValid() {
    if (!usuario.classList.contains("error") && !senha.classList.contains("error")) {
        return true;
    } else
        return false;
}