import React, {useState} from 'react';
import {useNavigate, Form} from "react-router";
import ValidatedInput from "~/common/ValidatedInput";

export default function ValidatedForm({children, actionData}:
                                          {
                                              children: React.ReactElement<typeof ValidatedInput> |
                                                  React.ReactElement<typeof ValidatedInput>[],
                                              actionData?: Record<string,
                                                  Record<string, string | object> | // data
                                                  Record<string, string[]> // errors
                                              >
                                          }) {
    const navigate = useNavigate();
    const [validated, setValidated] = useState(false);

    let childrenWithProps = React.Children.map(children, child => {
        if (React.isValidElement(child) && "showBeforeValidation" in child.props && child.props.showBeforeValidation) {
            const props = {validated};
            return React.cloneElement(child, props);
        }
        return child;
    });

    // when serverSideErrors appear
    if (actionData) {
        const {data, errors} = actionData;
        childrenWithProps = childrenWithProps.map(child => {
            if (React.isValidElement(child) && "children" in child.props) {
                const c = child.props.children;
                if (React.isValidElement(c) && c.props instanceof Object &&
                    "name" in c.props && typeof c.props?.name === "string") {
                    const newProp = {
                        defaultValue: data[c.props.name],
                        serverSideError: errors[c.props.name]
                    };
                    return React.cloneElement(child, newProp);
                }
            }
            return child;
        });
    }

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
            {childrenWithProps}
            <button type={"submit"}>Submit</button>
            <button onClick={() => navigate(-1)}>Cancel</button>
        </Form>
    );
}