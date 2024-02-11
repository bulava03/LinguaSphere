const tokenSetLesson = token;
const formDataSetLesson = formData;

function setLessonConfirm(time, day) {
    const formData = new FormData();
    formData.append('token', tokenSetLesson);
    formData.append('day', day);
    formData.append('time', time);
    formData.append('languageId', formDataSetLesson.languageId);
    formData.append('teacherEmail', formDataSetLesson.teacherEmail);

    const url = '/lesson/setLesson';

    fetch(url, {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        } else {
            td = document.getElementById('lesson_' + time + '_' + day);
            td.classList.remove('free');
            td.classList.add('lesson');
            td.onclick = '';
            td.setAttribute("onclick", "removeLessonConfirm(" + time + ", " + day + ")");
        }
    })
    .catch(error => {
        console.error('There was a problem with setting lesson operation.');
    });
}
