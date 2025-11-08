const setClickEventHandler = (btnSelector, statusSelector, openUrl, redirectUrl) => {
    const btn = document.querySelector(btnSelector);
    const sts = document.querySelector(statusSelector);

    btn.addEventListener("click", () => {
        const wnd = window.open(openUrl)
        if (wnd) {
            if (wnd.closed) {
                sts.innerText = openUrl + " window: закрыто";
            } else {
                sts.innerText = openUrl + " window: не закрыто";
                if (redirectUrl) {
                    setTimeout(() => {
                        wnd.location = redirectUrl;
                    }, 5000);
                }
            }
        } else {
            sts.innerText = openUrl + " window: не доступно";
        }
});
}