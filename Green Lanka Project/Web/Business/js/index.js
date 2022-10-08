// Your web app's Firebase configuration
var firebaseConfig = {
  apiKey: "AIzaSyDugFU8k8Oi9Uds1fai2EvrRDyM7R7_jDs",
  authDomain: "green-lanka-9257f.firebaseapp.com",
  databaseURL: "https://green-lanka-9257f-default-rtdb.firebaseio.com",
  projectId: "green-lanka-9257f",
  storageBucket: "green-lanka-9257f.appspot.com",
  messagingSenderId: "212171362642",
  appId: "1:212171362642:web:458981c531ba48dc2baa8b",
  measurementId: "G-90G73D2TPL"

};
// Initialize Firebase
firebase.initializeApp(firebaseConfig);

// Set database variable
var database = firebase.database()



function get() {
  var bid = document.getElementById('bid').value
 

var user_ref = database.ref('Business/' + bid)
user_ref.on('value', function(snapshot) {
  var data = snapshot.val()

  var fbid = data.bid;
  
  if(fbid === bid )
  {
      email.value = data.email;
      bname.value = data.bname;
      oname.value = data.name;
    }
  else
  {
      alert("wrong");
  }

})

}

function update() {
  var cbid = document.getElementById('bid').value
  var ubname = document.getElementById('bname').value
  var uname = document.getElementById('name').value
  var uemail = document.getElementById('email').value
  var pass = document.getElementById('pass').value
  var cpass = document.getElementById('cpass').value

    var user_ref = database.ref('Business/' + cbid)
    user_ref.on('value', function(snapshot) {
    var data = snapshot.val()
     
    var ubid = data.bid;

  if(cbid === "" || ubname === "" || uname === "" || uemail === "" ||pass === "" || cpass === "")
  {
    alert("Please fill all the details!")
  }
  else
  {
  
    if(ubid === "")
    {
      alert('Can\'t found Business Id ' + cbid + 'Please Check your business id!!')
    }
    else
    
    if(pass===cpass)
    {

      var updates = {
      bid : cbid,
      bname:ubname,
      name:uname,
      email:uemail,
      pass:pass
      
      }
      database.ref('Business/' + cbid).update(updates)

      alert('Updated!!')
    }
  
      else
      {
        alert('Please check your passwords again!!')
      }
    
    
  
  
}
})
}



function remove() {
  var username = document.getElementById('username').value

  database.ref('users/' + username).remove()

  alert('deleted')
}



function login() {
  var ubid = document.getElementById('bid').value
  var upass = document.getElementById('password').value

var user_ref = database.ref('Business/' + ubid)
user_ref.on('value', function(snapshot) {
  var data = snapshot.val()

  var bid = data.bid;
  var pass = data.pass;
  
  if(bid === ubid && pass === upass )
  {
      alert("Login Success !! Please Wait.");
      window.location.replace("business_my_business_details.html");
  }
  else
  {
      alert("Please try again!!! Wrong user name or password.");
  }

})

}