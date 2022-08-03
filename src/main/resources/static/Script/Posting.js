let baseurl = "http://localhost:8080/";

let btnPostTxn = document.getElementById("submitButton");

btnPostTxn.onclick = function(){postIndividualTransaction()};

const uploadFileButtonEl = document.getElementById("uploadFileButton");

uploadFileButtonEl.onclick = function(){postTransactionsFile()}

function postTransactionsFile(){
document.getElementById("repostingMessage").style.visibility="visible";
    var TransactionIdList=[];
        Papa.parse(document.getElementById("uploadedFile").files[0],{
            download:true,
            header:true,
            skipEmptyLines:true,
            complete: function(results){
               for (let i = 0; i < results.data.length; i++) {
                    TransactionIdList.push(results.data[i].Transaction_Id);
               }
               console.log(TransactionIdList);
               getPostingMessage(TransactionIdList);
            }

        });

}

function postIndividualTransaction(){
    document.getElementById("repostingMessage").style.visibility="visible";
    let txnId = document.getElementById("singleTxnText").value;
    var TransactionIdList= txnId.split(",");
    getPostingMessage(TransactionIdList);
    console.log(TransactionIdList)

}


function getPostingMessage(TransactionIdList){
    fetch(baseurl+"reposting", {
        method:'POST',
        headers:{
            'Content-Type':'application/json'
        },
        body: JSON.stringify({
            transactionIds:TransactionIdList
        })
    }).then((data)=>{
        return data.json();
    }).then((objectData)=>{
        let postingMessage="";
        postingMessage = objectData.message;
        document.getElementById("repostingMessage").innerHTML=postingMessage;
        console.log(objectData.message);
    }).catch(error => console.log(error))
}