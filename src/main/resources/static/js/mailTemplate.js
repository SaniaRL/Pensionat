const sendText = () => {
    const text = document.getElementsByClassName("mail-textarea").item(0).value;
    console.log(text);

    let xhr = new XMLHttpRequest();
    let url = "/mailTemplate/updateTemplate";

    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");

    const mailText = {
        content: text
    }

    let data = text;
    console.log(data);
    xhr.send(data);

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log(xhr.responseText);
            const parser = new DOMParser();
            const htmlDoc = parser.parseFromString(xhr.responseText, 'text/html');
            document.open();
            document.write(htmlDoc.documentElement.outerHTML);
            document.close();
        }
    }
}

function insertVariable(variableElement) {
    const variable = variableElement.textContent;
    const textarea = document.getElementById("mailText");
    textarea.value += '!!!!' + variable + '!!!!';
}