//purchase product
$('.purchaseBtn').on('click',function(event){
    const id = event.target.id;
    fetch("/purchase-product/"+id, {
        method: 'PUT',
    })
        .then(response => response.text())
        .then(data => {
            location.href = "/display-product/"+id;
            console.log(data);
        })
        .catch((error) => {
            console.error('Error:', error);
        });
})