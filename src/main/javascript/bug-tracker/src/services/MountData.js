import jwtDecode from "jwt-decode";

export async function MountData() {

    const token = localStorage.getItem("access_token");
    console.log(token);
    const data = jwtDecode(token);
    console.log(data);

    await fetch("http://localhost:8080/api/project/search/all",
    {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
    .then(response => {
        response.json().then(data => {
            localStorage.setItem("projects", JSON.stringify(data.data));
        })}
    );

    await fetch(`http://localhost:8080/api/user/search/${data.sub}`,
    {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
    .then(response => {
        response.json().then(data => {
            localStorage.setItem("user", JSON.stringify(data.data));
        })}
    );
}