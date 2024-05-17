const expand = (e) => {
    const clicked = e.parentElement;
    const dropdowns = document.getElementsByClassName("menu-titles").item(0).children;
    Array.from(dropdowns).forEach(d => {
        console.log(d.classList);
        if(d === clicked && !d.classList.contains("submenu-hidden")){
            d.classList.add("submenu-hidden");
        }
        else if(d === clicked && d.classList.contains("submenu-hidden")){
            d.classList.remove("submenu-hidden");
        }
        if(d !== clicked && !d.classList.contains("submenu-hidden")){
            d.classList.add("submenu-hidden");
        }
    })
}