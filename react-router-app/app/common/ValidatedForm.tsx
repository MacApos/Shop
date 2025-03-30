import React, {useEffect, useRef, useState} from 'react';
import {Form, useNavigate} from "react-router";
import ValidatedInput from "~/common/ValidatedInput";

type ValidatedFormProps = {
    children: React.ReactElement<typeof ValidatedInput> |
        React.ReactElement<typeof ValidatedInput>[],
    action?: string
    errors?: Record<string, string[]>
    shouldRevokeValidation?: boolean
}

export default function ValidatedForm(props: ValidatedFormProps) {
    const {children, errors, action, shouldRevokeValidation} = props;
    const navigate = useNavigate();
    const [validated, setValidated] = useState(false);
    const formRef = useRef<HTMLFormElement>(null);
    const form = formRef.current;

    const revokeValidation = () => {
        setValidated(false);
    };

    useEffect(() => {
        if (form && !validated) {
            form.querySelectorAll("input").forEach(input => {
                input.setCustomValidity("");
            });
        }
    }, [validated]);

    let childrenWithProps: any[] = React.Children.map(children, child => {
        let props = {};
        if ("showBeforeValidation" in child.props && child.props.showBeforeValidation) {
            props = {validated};
        }
        if (shouldRevokeValidation) {
            props = {...props, revokeValidation};
        }
        return Object.keys(props).length ? React.cloneElement(child, props) : child;
    });


// when serverSideErrors appear
    if (errors) {
        childrenWithProps = childrenWithProps.map(child => {
            // if (child.props && "children" in child.props &&
            //     child.props.children && child.props.children instanceof Object && "props" in child.props.children &&
            //     child.props.children.props && child.props.children.props instanceof Object && "name" in child.props.children.props &&
            //     child.props.children.props.name === "string" && child.props.children.props.name in errors) {
            //     const name = child.props.children.props.name;
            //     const props = {serverSideError: errors[name]};
            // }
            const name = child.props.children.props.name;
            if (name in errors) {
                return React.cloneElement(child, {serverSideError: errors[name]});
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
        <Form ref={formRef} method="post" action={action ?? ""} className={`${validated ? "was-validated" : ""}`}
              onSubmit={handleSubmit} noValidate>
            {childrenWithProps}
            <button type={"submit"}>Submit</button>
            <button type={"button"} onClick={() => navigate(-1)}>Cancel</button>
        </Form>
    );
}