function fullScreen(element) {
  if(element.requestFullScreen) {
    element.requestFullScreen();
  } else if(element.webkitRequestFullScreen ) {
    element.webkitRequestFullScreen();
  } else if(element.mozRequestFullScreen) {
    element.mozRequestFullScreen();
  }
}

function fullScreenCancel() {
  if(document.requestFullScreen) {
    document.requestFullScreen();
  } else if(document .webkitRequestFullScreen ) {
    document.webkitRequestFullScreen();
  } else if(document .mozRequestFullScreen) {
    document.mozRequestFullScreen();
  }
}