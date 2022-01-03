//deletes product
$('.hello').on('click',function(event){
    event.preventDefault();
    const id = event.target.id;
    console.log(id)
    fetch("/product/"+id, {
        method: 'DELETE',
    })
        .then(response => response.text())
        .then(data => {
            if(data=="denied"){
                location.href = "/";
            }
            location.href = "/admin-page";
            console.log(data);
        })
        .catch((error) => {
            console.error('Error:', error);
        });
})