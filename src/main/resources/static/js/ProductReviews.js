//deletes review
$('.del').on('click',function(event){
    event.preventDefault();
    const id = event.target.id;
    fetch("/review/"+id, {
        method: 'DELETE',
    })
        .then(response => response.text())
        .then(data => {
            location.href = "/admin-page";
            console.log(data);
        })
        .catch((error) => {
            console.error('Error:', error);
        });
})