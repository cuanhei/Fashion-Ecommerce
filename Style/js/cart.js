function updatePercent(percentDivID,percentage) {
    document.getElementById(percentDivID).style.background = `conic-gradient(
rgb(0, 173, 0) 0% ${percentage}%,
        whitesmoke ${percentage}% 100%
    )`;
}