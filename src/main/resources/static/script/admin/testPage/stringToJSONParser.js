function parseStringToObject(element) {
    var regex = /(\w+)\s*=\s*([^,]+)(?=,|$)/g;
    var result = {};
    var match;

    var filePart = element.substring(element.lastIndexOf("file="));
    element = element.substring(0, element.lastIndexOf("file="));
    while (match = regex.exec(filePart)) {
        var key = match[1];
        var value = match[2];
        if (value === 'null') {
            result[key] = null;
        } else {
            result[key] = value;
        }
    }
    var idMatch = element.match(/id=(\d+)/);
    if (idMatch) {
        result.id = parseInt(idMatch[1]);
    }
    var textMatch = element.match(/text=([^,]+)/);
    if (textMatch) {
        result.text = textMatch[1];
    }
    return result;
}
