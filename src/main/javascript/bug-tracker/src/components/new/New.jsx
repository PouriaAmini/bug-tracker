import React, { useState } from "react";
import Dropdown from "../dropdown/Dropdown";
import Badge from "../badge/Badge";
import { SearchAction } from "../../services/SearchAction";

const renderSearchItem = (item, index) => (
    <div className="search-item" key={index}>
        <span className="name__search-item">
            {
                item.firstName ? `${item.firstName} ${item.lastName}`: item.name
            }
        </span>
        <span className="data__search-item">
            {
                item.firstName ? item.email : item.briefDescription ? `${item.briefDescription.substring(0,50)}...` : ''
            }
        </span>
        <span className="priority__search-item">
            {
                item.name ?
                    <Badge
                        type={item.priorityAverage ? item.priorityAverage : item.priority}
                        content={`${item.priorityAverage ? item.priorityAverage : item.priority} priority`}
                    /> : ''
            }
        </span>
    </div>
)

const renderSearchToggle = (onFetch, type) => {
    return (
        <div className="assign__toggle">
            <input
                type="text"
                placeholder={`Search ${type === "Bug" ? "Group" : type === "Group" ? "Project" : "User"}...`}
                onChange={(e) => onFetch(e.target.value, type)}
            />
        </div>
    )
}

const New = (props) => {

    let url = '';
    const [search, setSearch] = useState([]);
    const [fields, setFields] = useState({
        assignTo : []
    });
    const [errors, setErrors] = useState({});
    const token = localStorage.getItem("access_token");

    const onFetch = (newUrl, type) => {
        if(newUrl.trim()){
            url = newUrl;
            SearchAction(url, token, [])
                .then(
                    response => setSearch(response.filter((data) => (
                            type === "Bug" ? data.bugs !== undefined :
                                type === "User" ? data.firstName !== undefined :
                                    type === "Group" ? data.groups !== undefined :
                                        false
                        )
                    )
                ));

        }
        else if(!newUrl.trim()) {
            setSearch([]);
        }
    }

    const renderSearchItem = (item, index) => (
        <div className="search-item" key={index} onClick={() => {
            return props.type !== "User" ? fields.belongsTo = item : fields.assignTo.push(item);
        }}>
        <span className="name__search-item">
            {
                item.firstName ? `${item.firstName} ${item.lastName}`: item.name
            }
        </span>
            <span className="data__search-item">
            {
                item.firstName ? item.email : item.briefDescription ? `${item.briefDescription.substring(0,50)}...` : ''
            }
        </span>
            <span className="priority__search-item">
            {
                item.name ?
                    <Badge
                        type={item.priorityAverage ? item.priorityAverage : item.priority}
                        content={`${item.priorityAverage ? item.priorityAverage : item.priority} priority`}
                    /> : ''
            }
        </span>
        </div>
    )

    const handleValidation = () => {
        let fieldsObject = fields;
        let errorsObject = {};
        let isValid = true;

        if((!fieldsObject.name)) {
            isValid = false;
            errorsObject.name = "Cannot be empty!";
        }
        else if (typeof fieldsObject.firstName !== "undefined") {
            if(
                fieldsObject.firstName.length < 3
            ) {
                isValid = false;
                errorsObject.firstName = "Minimum length is 3!";
            }
        }

        if(!fieldsObject.priority) {
            isValid = false;
            errorsObject.priority = "Select an option!";
        }

        setErrors(errorsObject);
        return isValid;

    }

    const contactSubmit = (e) => {

        e.preventDefault();

        if (handleValidation()) {

        }
    }

    const handleChange = (field, e) => {
        const { value } = e.target;
        setFields({
            ...fields,
            [field]: value
        })
    }

    return (
        <form
            className='new__form'
            onSubmit={contactSubmit}
        >
            <div>
                <h2 className="page-header">
                    New {props.type}
                </h2>
            </div>
            <div className="new__name-toggle">
                <label htmlFor={`new${props.type}`} style={{ color: "red" }}>{errors.name}</label>
                <input
                    type="text"
                    id={`new${props.type}`}
                    placeholder={`Name`}
                    onChange={(e) => handleChange("name", e)}
                    value={fields.name || ''}
                    style={
                        errors.name ? { borderColor: "red"} : { borderColor: "#ffffff1a"}
                    }
                />
            </div>
            <div className="brief__description-toggle">
                <label htmlFor={`bdescription${props.type}`}>{`Brief Description:`}</label>
                <input
                    type="text"
                    id={`bdescription${props.type}`}
                    placeholder={`Brief Description`}
                    value={fields.briefDescription || ''}
                />
            </div>
            {
                props.type === "Bug" ?
                    <>
                        <div className="full__description-toggle">
                            <label htmlFor={`fdescription${props.type}`}>{`Full Description:`}</label>
                            <input
                                type="text"
                                id={`fdescription${props.type}`}
                                placeholder={`Full Description`}
                                value={fields.fullDescription || ''}
                            />
                        </div>
                        <div className="priority__toggle">
                            <label htmlFor="priority" style={{ color: "red" }}>{errors.priority}</label>
                            <select
                                className='priority'
                                onChange={(e) => handleChange("priority", e)}
                                style={
                                    errors.priority ? { borderColor: "red"} : { borderColor: "#ffffff1a"}
                                }
                            >
                                <option value="">Select {`${props.type}`}'s Priority</option>
                                <option value="high">High Priority</option>
                                <option value="medium">Medium Priority</option>
                                <option value="low">Low Priority</option>
                                <option value="unknown">Unknown Priority</option>
                            </select>
                        </div>
                        <label htmlFor={`assign-to__toggle`}>{`Assign to Users:`}</label>
                        <div className="assign-to__toggle">
                            <Dropdown
                                customToggle={() => renderSearchToggle(onFetch, "User")}
                                contentData={search}
                                renderItems={(item, index) => renderSearchItem(item, index)}
                            />
                        </div>
                    </> : ''
            }
            {
                props.type !== "Project" ?
                    <>
                        <label htmlFor={`within-${props.type}__toggle`}>{`Category:`}</label>
                        <div className={`within-${props.type}__toggle`}>
                            <Dropdown
                                customToggle={() => renderSearchToggle(onFetch, props.type)}
                                contentData={search}
                                renderItems={(item, index) => renderSearchItem(item, index)}
                            />
                        </div>
                    </>
                    : ''
            }
            <button className='create-button'>Create</button>
        </form>
    );
};

export default New;