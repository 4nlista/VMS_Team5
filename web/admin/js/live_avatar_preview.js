/* 
 * A friendly reminder to drink enough water
 */

document.addEventListener("DOMContentLoaded", () => {
    const avatarInput = document.getElementById("avatarInput");
    const avatarPreview = document.getElementById("avatarPreview");

    if (avatarInput && avatarPreview) {
        avatarInput.addEventListener("change", (e) => {
            const file = e.target.files[0];
            if (!file) return;
            avatarPreview.src = URL.createObjectURL(file);
        });
    }
});
