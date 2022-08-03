var pageNumber = 0;
//let previousPageEl=document.getElementById("previousPage");
//
document.getElementById("pageNumber").defaultValue = 0;



function previousPage(){
    let searchTextEl= document.getElementById("searchInput");
    let searchText=searchTextEl.value;



    let currentPageEl = document.getElementById("currentPage");
    let currentPage= currentPageEl.textContent;

    let orgCode = document.getElementById("orgCode");
        var selectedOrgcode=[];
        for(var i = 0; i < orgCode.selectedOptions.length; i++){
        selectedOrgcode.push(orgCode.selectedOptions[i].value);
        }

    if(searchText != ""){
    search(parseInt(currentPage)-1);

    }else if(selectedOrgcode.length >0){
             filterTransaction(parseInt(currentPage)-1);

         }
    else{
        getTransactions(parseInt(currentPage)-1);
    }
}

function nextPage(){
    let searchTextEl= document.getElementById("searchInput");
    let searchText=searchTextEl.value;
    let startDateValue = document.getElementById("startDate").value;
    let endDateValue = document.getElementById("endDate").value;
    console.log(endDateValue);

    let currentPageEl = document.getElementById("currentPage");
    let currentPage = currentPageEl.textContent;

    let orgCode = document.getElementById("orgCode");
    var selectedOrgcode=[];
    for(var i = 0; i < orgCode.selectedOptions.length; i++){
    selectedOrgcode.push(orgCode.selectedOptions[i].value);
    }


    if(searchText != ""){
        search(parseInt(currentPage)+1);


    }else if(selectedOrgcode.length > 0 || (startDateValue!= "" && endDateValue !="")){
        filterTransaction(parseInt(currentPage)+1);
        console.log("hello");
    }
    else{
        getTransactions(parseInt(currentPage)+1);
        console.log("verelev");
    }
}


function getTags(){
    let options = {
            method:"POST"
    }
    let url="http://localhost:8080/orgCodes";
    fetch(url,options)
    .then((data)=>{
    return data.json();
    }).then((objectData)=>{

    let filterData="";
    objectData.map((values)=>{
    filterData+=`<option style="padding:3px;" value=${values}>${values}</option>`;

    })
    document.getElementById("orgCode").innerHTML=filterData;
    })

}

function getTransactions(page){

    var input = document.getElementById("pageNumber");


    let itemsRangeValue = 25;

    let searchForDownloadEl = document.getElementById("searchForDownload");
    searchForDownloadEl.value = "";

    let orgCodeForDownloadEl = document.getElementById("orgCodesForDownloads");
    orgCodeForDownloadEl.value = "";

    let startDateForDownloadEl = document.getElementById("startDateForDownloads");
    startDateForDownloadEl.value = "";

    let endDateForDownloadEl = document.getElementById("endDateForDownload");
    endDateForDownloadEl.value = "";

    let previousPageEl=document.getElementById("previousPage");
    let nextPageEl = document.getElementById("nextPage");


    let currentPageEl = document.getElementById("currentPage");
    currentPageEl.textContent = page;

    let options = {
        method:"POST",
        headers: {
                    "Content-Type": "application/json"
                  },
        body: JSON.stringify({
                    transactionsRange:itemsRangeValue
                })
    }

    let url="http://localhost:8080/unsuccessfulTransactions?page="+page;
    let firstPage;
    let lastPage;
    fetch(url,options)
    .then((data)=>{
    return data.json();
    }).then((objectData)=>{

    input.setAttribute("max",(objectData.totalPages)-1);

    let tableData="";

    firstPage = objectData.first;
    let totalElement = objectData.totalElements;

    if(firstPage == true){
        previousPageEl.style.visibility = "hidden";
    }else{
        previousPageEl.style.visibility = "visible";
    }

    lastPage = objectData.last;

    if(lastPage == true){
        nextPageEl.style.visibility = "hidden";
    }else{
        nextPageEl.style.visibility = "visible";
    }

    objectData.content.map((values)=>{
    const date = ((values.updatedDate));
    let year = date.slice(0,4);
    let month= date.slice(5,7);
    let day = date.slice(8,10);
    let time = date.slice(12,19);


    let dateStr = day +"-"+month+"-"+year;

    tableData +=`<tr>
                         <td>${values.txnId}</td>
                         <td>${values.status}</td>
                         <td>${values.orgCode}</td>
                         <td>${values.postingCount}</td>
                         <td>${dateStr}</td>
                     </tr>`;
        });
    document.getElementById("tableBody").innerHTML=tableData;
    });
}



function search(pageNumber) {

        var input = document.getElementById("pageNumber");

        let searchForDownloadEl = document.getElementById("searchForDownload");
        let currentPageEl = document.getElementById("currentPage");
        currentPageEl.textContent = pageNumber;
        let previousPageEl=document.getElementById("previousPage");
        let nextPageEl = document.getElementById("nextPage");

            let orgCodeForDownloadEl = document.getElementById("orgCodesForDownloads");
            orgCodeForDownloadEl.value = "";

            let startDateForDownloadEl = document.getElementById("startDateForDownloads");
            startDateForDownloadEl.value = "";

            let endDateForDownloadEl = document.getElementById("endDateForDownload");
            endDateForDownloadEl.value = "";



  let page = pageNumber;

  let searchTextEl= document.getElementById("searchInput");
  let searchText=searchTextEl.value;
  if(searchText == ""){
    alert("Search field should not be empty.");
  }else{

  searchForDownloadEl.value=searchText;

  let url1="http://localhost:8080/transactionsWithFilter";
  let options1 = {
        method: 'POST',
        headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify({
              searchVariable:searchText,
              pageNo:page
          })
        }
  let responseObject = fetch(url1, options1);
  let firstPage;
  let lastPage;
  responseObject.then((data)=>{
  return data.json();
  })
  .then((objectData)=>{
         input.setAttribute("max",(objectData.totalPages)-1);
         let tableData="";
         firstPage = objectData.first;

         if(firstPage == true){
         previousPageEl.style.visibility = "hidden";
         }else{
         previousPageEl.style.visibility = "visible";
         }

         lastPage = objectData.last;

         if(lastPage == true){
            nextPageEl.style.visibility = "hidden";
         }else{
            nextPageEl.style.visibility = "visible";
         }
         let totalElement = objectData.totalElements;
         console.log(totalElement);
         if(totalElement >0){
          console.log(totalElement);


         objectData.content.map((values)=>{

            const date = ((values.updatedDate));
            let year = date.slice(0,4);
            let month= date.slice(5,7);
            let day = date.slice(8,10);
            let time = date.slice(12,19);


            let dateStr = day +"-"+month+"-"+year;

         tableData +=`<tr>
                              <td>${values.txnId}</td>
                              <td>${values.status}</td>
                              <td>${values.orgCode}</td>
                              <td>${values.postingCount}</td>
                              <td>${dateStr}</td>
                          </tr>`;
             }

             )}else{
             tableData += "No Transactions Found"
             };
      document.getElementById("tableBody").innerHTML=tableData;
  })};


}

function filterTransaction(pageNumber){
    var input = document.getElementById("pageNumber");

    let searchForDownloadEl = document.getElementById("searchForDownload");
    searchForDownloadEl.value=""

    let searchTextEl= document.getElementById("searchInput");
    searchTextEl.value = "";

    let orgCodeForDownloadEl = document.getElementById("orgCodesForDownloads");


    let startDateForDownloadEl = document.getElementById("startDateForDownloads");


    let endDateForDownloadEl = document.getElementById("endDateForDownload");


    let startDateEl = document.getElementById("startDate");
    let endDateEl = document.getElementById("endDate");
    let startDateValue = startDateEl.value;
    let endDateValue = endDateEl.value;
    console.log("x");
    console.log(endDateValue);


     let currentPageEl = document.getElementById("currentPage");
     currentPageEl.textContent = pageNumber;
     let previousPageEl=document.getElementById("previousPage");
     let nextPageEl = document.getElementById("nextPage");



    let page=pageNumber;
    let orgCode = document.getElementById("orgCode");

    var selectedFilters=[];


    for (var i = 0; i < orgCode.selectedOptions.length; i++) {
       selectedFilters.push(orgCode.selectedOptions[i].value);
    }
    console.log(selectedFilters)
    orgCodeForDownloadEl.value = selectedFilters;
    startDateForDownloadEl.value = startDateValue;
    endDateForDownloadEl.value = endDateValue;



    let firstPage;
    let lastPage;
    fetch("http://localhost:8080/unsuccessfulTransactionsWithFilter?page="+page, {
        method:'POST',
        headers:{
            'Content-Type':'application/json'
        },
        body: JSON.stringify({
            orgCodes:selectedFilters,
            startDate:startDateValue,
            endDate:endDateValue
        })
    }).then((data)=>{
        return data.json();
    }).then((objectData)=>{
        input.setAttribute("max",(objectData.totalPages)-1);
        let tableData="";

        firstPage = objectData.first;

         if(firstPage == true){
         previousPageEl.style.visibility = "hidden";
         }else{
         previousPageEl.style.visibility = "visible";
         }

         lastPage = objectData.last;

         if(lastPage == true){
            nextPageEl.style.visibility = "hidden";
         }else{
            nextPageEl.style.visibility = "visible";
         }

         let totalElement = objectData.totalElements;
         console.log(totalElement);
         if(totalElement >0){
         console.log(totalElement);

        objectData.content.map((values)=>{

        const date = ((values.updatedDate));
        let year = date.slice(0,4);
        let month= date.slice(5,7);
        let day = date.slice(8,10);
        let time = date.slice(12,19);


        let dateStr = day +"-"+month+"-"+year;


                 tableData +=`<tr>
                                      <td>${values.txnId}</td>
                                      <td>${values.status}</td>
                                      <td>${values.orgCode}</td>
                                      <td>${values.postingCount}</td>
                                      <td>${dateStr}</td>
                                  </tr>`;
                     })}else{
                          tableData = tableData+"No Transactions Found";
                     };

              document.getElementById("tableBody").innerHTML=tableData;

    })
}

getTransactions(pageNumber);
getTags();


const objectToCSV = function(data){
    const csvRows = [];

    const headers =Object.keys(data[0]);
    csvRows.push(headers.join(','));


    for(const row of data){
        const values = headers.map(header=>{

            const escaped = (''+row[header]);
            return `"${escaped}"`;
        });
        csvRows.push(values.join(','));
    }

    return csvRows.join('\n');

}


const download = function(data){

    const blob =new Blob([data],{ type: 'text/csv'});
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.setAttribute('hidden','');
    a.setAttribute('href',url);
    a.setAttribute('download','download.csv');
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
}




async function downloadCSV(){
    let searchForDownloadEl = document.getElementById("searchForDownload");
    let orgCodeForDownloadEl = document.getElementById("orgCodesForDownloads");
    let startDateForDownloadEl = document.getElementById("startDateForDownloads");
    let endDateForDownloadEl = document.getElementById("endDateForDownload");

    let searchedText = searchForDownloadEl.value;
    let orgCodesList = orgCodeForDownloadEl.value;
    let startDateText = startDateForDownloadEl.value;
    let endDateText = endDateForDownloadEl.value;


     let orgCode = document.getElementById("orgCode");

        var selectedFilters=[];


        for (var i = 0; i < orgCode.selectedOptions.length; i++) {
           selectedFilters.push(orgCode.selectedOptions[i].value);
        }
    console.log(selectedFilters);

    let options = {
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
              },
              body: JSON.stringify({
                  search:searchedText,
                  orgCodes:selectedFilters,
                  startDAte:startDateText,
                  endDate:endDateText
              })
            }

    let url="http://localhost:8080/downloadCSVFile";

    const result =await fetch(url,options);


    let datafile = await result.json();

    const data = datafile.map(row=>({
        id:row.id,
        txnId:row.txnId,
        externalRefNumber:row.externalRefNumber,
        postingStatus:row.postingStatus,
        postingCount:row.postingCount,

        postingRequest:row.postingRequest,
        orgCode:row.orgCode,
        createdDate:row.createdDate,
        updatedDate:row.updatedDate,
        status:row.status,
        paymentMode:row.paymentMode,
        txn:row.txn
    }));

    const csvData = objectToCSV(data);
    console.log(csvData);
    download(csvData);
}

function setPageNumber(){
    let inputPageEl = document.getElementById("pageNumber");

    let searchTextEl= document.getElementById("searchInput");
        let searchText=searchTextEl.value;
        let startDateValue = document.getElementById("startDate").value;
        let endDateValue = document.getElementById("endDate").value;




        let orgCode = document.getElementById("orgCode");
        var selectedOrgcode=[];
        for(var i = 0; i < orgCode.selectedOptions.length; i++){
        selectedOrgcode.push(orgCode.selectedOptions[i].value);
        }


        if(searchText != ""){
            search(parseInt(inputPageEl.value));


        }else if(selectedOrgcode.length > 0 || (startDateValue!= "" && endDateValue !="")){
            filterTransaction(parseInt(inputPageEl.value));
            console.log("hello");
        }
        else{
            getTransactions(parseInt(inputPageEl.value));
            console.log("verelev");
        }

}



