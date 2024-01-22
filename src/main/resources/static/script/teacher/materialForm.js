function addTextarea() {
    const container = document.getElementsByClassName('regInput')[0];
    const button = document.getElementsByClassName('button')[0];
    const newTextarea = document.createElement('textarea');
    newTextarea.className = 'field';
    newTextarea.type = 'text';
    newTextarea.name = 'links';
    container.insertBefore(newTextarea, button);

    checkAndRemoveEmptyTextareas();
}

function checkAndRemoveEmptyTextareas() {
    const container = document.getElementsByClassName('regInput')[0];

    const textareas = container.getElementsByTagName('textarea');

    Array.from(textareas).forEach((item, index) => {
    if (index < textareas.length - 1 && item.value.trim() === '') {
        container.removeChild(item);
    }
    });
}
