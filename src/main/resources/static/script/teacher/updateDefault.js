var inputImg = document.getElementById('fileImg');
const base64StringImage = document.getElementById('imageToUploadByDefault').innerHTML;

if (base64StringImage != "") {
    const byteCharacters = atob(base64StringImage);
    const byteNumbers = new Array(byteCharacters.length);
    for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
    }
    const byteArray = new Uint8Array(byteNumbers);

    const file = new File([byteArray], "image.jpeg", {type:"image/jpeg"});

    var dt = new DataTransfer();
    dt.items.add(file);
    const file_list = dt.files;

    inputImg.files = file_list;
}

var inputFile = document.getElementById('file');
const base64StringFile = document.getElementById('fileToUploadByDefault').innerHTML;
const fileType = document.getElementById('fileTypeByDefault').innerHTML;

if (base64StringFile != "") {
    const byteCharacters = atob(base64StringFile);
    const byteNumbers = new Array(byteCharacters.length);
    for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
    }
    const byteArray = new Uint8Array(byteNumbers);

    switch (fileType) {
        case "application/pdf":
            fileName = "document.pdf";
            fileExtension = "pdf";
            break;
        case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
            fileName = "document.docx";
            fileExtension = "docx";
            break;
        case "video/mp4":
            fileName = "video.mp4";
            fileExtension = "mp4";
            break;
        default:
            fileName = "file";
            fileExtension = "bin";
    }

    const file = new File([byteArray], fileName + '.' + fileExtension, { type: fileType });

    var dt = new DataTransfer();
    dt.items.add(file);
    const file_list = dt.files;

    inputFile.files = file_list;
}
