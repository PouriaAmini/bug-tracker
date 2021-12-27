import { MountData } from "./MountData";

export async function LoginAction(email, password){

    let details = {
    "email": email,
    "password": password
    };

    let formBody = [];
    for (let property in details) {
        let encodedKey = property;
        let encodedValue = details[property];
    formBody.push(encodedKey + "=" + encodedValue);
    }
    formBody = formBody.join("&");

    await fetch('http://localhost:8080/api/login', {
          method: 'POST',
          headers: {
              'Content-Type': 'application/x-www-form-urlencoded'
          },
          body: formBody
    }).then((response) => {
        response.json().then((data) => {
            if(data.message === "Unauthorized") {
                alert("The email or password is wrong!")
            }
            else {
                localStorage.setItem("access_token", data.access_token);
                localStorage.setItem("refresh_token", data.refresh_token);
                MountData().then(() =>
                    window.location.reload()
                );
            }
        });
    });
}