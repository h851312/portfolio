$(function (){

  const fs = $('.fs'),
  fsLst = fs.find('ul'),
  fsBtn = fs.find('.fsBtn');

  let state = 0;
  fsBtn.click(function () {

  fsLst.slideToggle();
  if (state == 0) {
  $('.fs i').attr({ class: 'fa-solid fa-plus' });
  state = 1;
} else {
  $('.fs i').attr({ class: 'fa-solid fa-minus' });
  state = 0;
}
  return false;
});
});

document.addEventListener("DOMContentLoaded", function () {
const footerLink = document.querySelector(".footer a[href='#top']");

if (footerLink) {
  footerLink.addEventListener("click", function (e) {
      e.preventDefault();
      window.scrollTo({
          top: 0,
          behavior: "smooth" // 부드러운 스크롤을 원하면 추가
      });
  });
}
});