function addTextarea() {
    const container = document.getElementsByClassName('regInput')[0];
    const button = document.getElementsByClassName('button')[0];
    const newTextarea = document.createElement('textarea');
    newTextarea.className = 'field';
    newTextarea.type = 'text';
    newTextarea.name = 'contacts';
    container.insertBefore(newTextarea, button);

    checkAndRemoveEmptyTextareas();
}

function checkAndRemoveEmptyTextareas() {
    const container = document.getElementsByClassName('regInput')[0];

    const textareasTemp = container.getElementsByTagName('textarea');
    const textareasArray = Array.from(textareasTemp);
    const textareas = textareasArray.filter(textarea => textarea.getAttribute('name') !== 'aboutMe');

    Array.from(textareas).forEach((item, index) => {
    if (index < textareas.length - 1 && item.value.trim() === '') {
        container.removeChild(item);
    }
    });
}
