const tokenRemoveLesson = token;
const formDataRemoveLesson = formData;

function removeLessonConfirm(time, day) {
    const formData = new FormData();
    formData.append('token', tokenSetLesson);
    formData.append('day', day);
    formData.append('time', time);
    formData.append('languageId', formDataSetLesson.languageId);
    formData.append('teacherEmail', formDataSetLesson.teacherEmail);

    const url = '/lesson/removeLesson';

    fetch(url, {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        } else {
            td = document.getElementById('lesson_' + time + '_' + day);
            td.classList.remove('lesson');
            td.classList.add('free');
            td.onclick = '';
            td.setAttribute("onclick", "setLessonConfirm(" + time + ", " + day + ")");
        }
    })
    .catch(error => {
        console.error('There was a problem with removing lesson operation.');
    });
}
