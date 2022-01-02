//activate or deactivate user
$('.control').on('click',function(event){
    event.preventDefault();
    const id = event.target.id;
    fetch("/user/change-status/"+id, {
        method: 'PUT',
    })
        .then(response => response.text())
        .then(data => {
            location.href = "/user-list";
            console.log(data);
        })
        .catch((error) => {
            console.error('Error:', error);
        });
})