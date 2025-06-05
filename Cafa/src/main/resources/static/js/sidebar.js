document.addEventListener('DOMContentLoaded', function() {
    const currentPath = window.location.pathname; // Mendapatkan path URL saat ini

    // Dapatkan semua link di sidebar
    const sidebarLinks = document.querySelectorAll('#sidebar ul li a');

    // Loop melalui setiap link
    sidebarLinks.forEach(link => {
        const linkPath = link.getAttribute('href'); // Dapatkan href dari link

        // Jika path URL saat ini cocok dengan href link, tambahkan class "active"
        if (currentPath === linkPath) {
            link.classList.add('active');
            link.parentNode.classList.add('active'); // Tambahkan ke <li> juga
        }
    });
});