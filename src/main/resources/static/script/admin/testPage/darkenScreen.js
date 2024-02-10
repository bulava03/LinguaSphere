function darkenScreenDefault(overlay, formContainer, button) {
    overlay.style.display = 'block';
    overlay.style.position = 'fixed';
    overlay.style.top = '0';
    overlay.style.left = '0';
    overlay.style.width = '100%';
    overlay.style.height = '100%';
    overlay.style.backgroundColor = 'rgba(0, 0, 0, 0.5)';
    overlay.style.zIndex = '999';

    formContainer.style.display = 'block';
    formContainer.style.position = 'fixed';
    formContainer.style.top = '50%';
    formContainer.style.left = '50%';
    formContainer.style.transform = 'translate(-50%, -50%)';
    formContainer.style.backgroundColor = 'white';
    formContainer.style.padding = '30px';
    formContainer.style.borderRadius = '5px';
    formContainer.style.zIndex = '1000';
    formContainer.style.textAlign = 'center';
    formContainer.style.borderRadius = '20px';
    formContainer.style.textAlign = 'center';

    document.body.style.overflow = 'hidden';
}

function darkenScreen(overlay, formContainer, button) {
    darkenScreenDefault(overlay, formContainer, button);

    overlay.addEventListener('click', function() {
        document.body.removeChild(overlay);
        document.body.removeChild(formContainer);
        document.body.style.overflow = 'auto';
    });

    formContainer.addEventListener('click', function(event) {
        event.stopPropagation();
    });

    button.addEventListener('click', function() {
        document.body.removeChild(overlay);
        document.body.removeChild(formContainer);
        document.body.style.overflow = 'auto';
    });
}

function darkenScreenWithFile(overlay, formContainer, button, buttonDelete) {
    darkenScreenDefault(overlay, formContainer, button);

    overlay.addEventListener('click', function() {
        document.body.removeChild(overlay);
        document.body.removeChild(formContainer);
        document.body.style.overflow = 'auto';
    });

    formContainer.addEventListener('click', function(event) {
        event.stopPropagation();
    });

    button.addEventListener('click', function() {
        document.body.removeChild(overlay);
        document.body.removeChild(formContainer);
        document.body.style.overflow = 'auto';
    });

    buttonDelete.addEventListener('click', function() {
        document.body.removeChild(overlay);
        document.body.removeChild(formContainer);
        document.body.style.overflow = 'auto';
    });
}
