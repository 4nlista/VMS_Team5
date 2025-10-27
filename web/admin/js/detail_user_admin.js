/* 
 * A friendly reminder to drink enough water
 */

(function () {
  // Auto-scale the .profile-card so the page never scrolls. This will shrink the card if needed.
  var card = document.querySelector('.profile-card');
  var container = document.querySelector('.main-content');
  if (!card || !container) return;

  function fitCard() {
    // Ensure no page scroll
    document.documentElement.style.overflow = 'hidden';
    document.body.style.overflow = 'hidden';

    // available size inside main-content (leave small margin)
    var availW = container.clientWidth - 20;
    var availH = container.clientHeight - 20;

    // reset transform to measure natural size
    card.style.transform = '';
    var rect = card.getBoundingClientRect();
    var cardW = rect.width;
    var cardH = rect.height;

    // compute scale (never upscale, only shrink)
    var scaleX = availW / cardW;
    var scaleY = availH / cardH;
    var scale = Math.min(scaleX, scaleY, 1);

    card.style.transformOrigin = 'top left';
    card.style.transform = 'scale(' + scale + ')';

    // center the scaled card inside container
    var offsetX = (container.clientWidth - (cardW * scale)) / 2;
    var offsetY = (container.clientHeight - (cardH * scale)) / 2;
    card.style.position = 'relative';
    card.style.left = offsetX + 'px';
    card.style.top = offsetY + 'px';
  }

  // run on load and resize
  window.addEventListener('load', fitCard);
  window.addEventListener('resize', fitCard);

  // If content changes dynamically, re-fit after slight delay
  var mo = new MutationObserver(function () {
    setTimeout(fitCard, 80);
  });
  mo.observe(card, { childList: true, subtree: true });
})();
