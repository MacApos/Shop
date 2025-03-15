import React, {useState} from 'react';
import {Form, useNavigate} from "react-router";

export default function ValidatedForm({children}: { children: React.ReactElement | React.ReactElement[] }) {
    const navigate = useNavigate();
    const [validated, setValidated] = useState(false);

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>): void => {
        const form = event.currentTarget;
        if (!form.checkValidity()) {
            event.preventDefault();
            event.stopPropagation();
        }
        setValidated(true);
    };

    return (
        <Form method="post" className={`${validated ? "was-validated" : ""}`} onSubmit={handleSubmit} noValidate>
            {children}
            <button type={"submit"}>Submit</button>
            <button onClick={() => navigate(-1)}>Cancel</button>
        </Form>
    );
}