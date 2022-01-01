$('.msg').hide();

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

//submit review
$('.review-form').on('submit',async function (event){
    event.preventDefault()
    const id = event.target.id.substring(1);
    const content = document.getElementById('content').value
    const rating = document.getElementById('rating').value

    let ip = await fetch('https://api.ipify.org/')
        .then(promise=>promise.text())
        .then(data=>{
            return data
        });
    const data = JSON.stringify({rating,content,ip});
    console.log(data)

    fetch("/create-review/"+id, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: data,
    })
        .then(response => response.text())
        .then(data => {
            if(data=="success"){
                location.href = "/display-product/"+id;
            }
            else{
                $('.msg').html(data);
                $('.msg').show();
                window.scrollTo(0, 0);
                console.log(data);
            }
        })
        .catch((error) => {
            console.error('Error:', error);
        });

});