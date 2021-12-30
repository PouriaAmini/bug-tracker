
export async function SearchAction(url, token, searchResult) {

    function getUser() {

        return fetch(`http://localhost:8080/api/user/search/${url}`,
            {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            })
            .then(response => (
                response.json().then(data => (
                    searchResult.push(...data.data["Users"])
                )))
            );
    }

    function getProject() {

        return fetch(`http://localhost:8080/api/project/search/${url}`,
            {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            })
            .then(response => (
                response.json().then(data => (
                    searchResult.push(...data.data["Projects"])
                )))
            );
    }

    function getGroup() {

        return fetch(`http://localhost:8080/api/group/search/${url}`,
            {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            })
            .then(response => (
                response.json().then(data => (
                    searchResult.push(...data.data["Groups"])
                )))
            );
    }

    function getBug() {

        return fetch(`http://localhost:8080/api/bug/search/${url}`,
            {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            })
            .then(response => (
                response.json().then(data => {
                    searchResult.push(...data.data["Bugs"])
                }))
            );
    }

    function getSearchResult() {
        return Promise.all([getBug(), getGroup(), getProject(), getUser()])
    }

    return getSearchResult().then(response => {
        return searchResult;
    });
}