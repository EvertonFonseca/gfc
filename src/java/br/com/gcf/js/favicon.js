function change_favicon(img) {
    var favicon = document.querySelector('link[rel="shortcut icon"]');
    
    if (!favicon) {
        favicon = document.createElement('link');
        favicon.setAttribute('rel', 'shortcut icon');
        var head = document.querySelector('head');
        head.appendChild(favicon);
    }
    
    
    favicon.setAttribute('type', 'image/png');
    favicon.setAttribute('href', img);
}

change_favicon('https://codepad.co/img/icn_logo.png');