
export async function SearchAction(url, token, searchResult) {

    await fetch(`http://localhost:8080/api/user/search/${url}`,
        {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
        .then(response => {
            response.json().then(data => {
                searchResult.push(...data.data["Users"])
            })}
        );

    await fetch(`http://localhost:8080/api/project/search/${url}`,
        {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
        .then(response => {
            response.json().then(data => {
                searchResult.push(...data.data["Projects"])
            })}
        );

    await fetch(`http://localhost:8080/api/group/search/${url}`,
        {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
        .then(response => {
            response.json().then(data => {
                searchResult.push(...data.data["Groups"])
            })}
        );

    await fetch(`http://localhost:8080/api/bug/search/${url}`,
        {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
        .then(response => {
            response.json().then(data => {
                searchResult.push(...data.data["Bugs"])
            })}
        );
    return searchResult;
}