import React, {useEffect, useState} from 'react';

interface ValidatedInputProps {
    label?: string;
    defaultValue?: string;
    defaultError?: string[];
    serverSideError?: string[];
    validated?: boolean;
    showBeforeValidation?: boolean;
    validationFunction?: (value: string) => string[];
    children: React.ReactElement<HTMLInputElement>;
}

export default function ValidatedInput(props: ValidatedInputProps) {
    const {
        children, defaultValue, defaultError, serverSideError, validated, showBeforeValidation,
        validationFunction
    } = props;
    const childProps = children.props;
    const name = childProps.name;
    const minLength = childProps.minLength ?? 3;

    let {label} = props;
    label = label ?? name.charAt(0).toUpperCase() + name.slice(1).toLowerCase();
    const invalidInput = `Invalid ${label.toLowerCase()}`;

    const [value, setValue] = useState(defaultValue ?? childProps.defaultValue ?? "");
    const [errorMessages, setErrorMessages] = useState<string[]>(defaultError || []);
    const [infoFeedback, setInfoFeedback] = useState<string>();

    const getInput = (value: string = name) => {
        const input = document.querySelector(`input[name='${value}']`);
        if (input instanceof HTMLInputElement) {
            return input;
        }
    };

    useEffect(() => {
        if (serverSideError) {
            const input = getInput();
            if (input) {
                input.setCustomValidity(invalidInput);
                setErrorMessages(serverSideError);
            }
        }
    }, [serverSideError]);

    useEffect(() => {
        const input = getInput();
        if (input) {
            if (validationFunction) {
                const validationErrors = validationFunction(value);
                input.setCustomValidity(validationErrors.length > 0 ? invalidInput : "");
                setErrorMessages(validationErrors);
            } else if (!defaultError || defaultError.length < 1) {
                setErrorMessages([value === "" ? invalidInput :
                    `${label} must be at least ${minLength} characters long.`]);
            }
        }
    }, [value]);

    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const targetValue = event.currentTarget.value;
        if (serverSideError) {
            event.currentTarget.setCustomValidity("");
        }
        setValue(targetValue);
    };

    return (
        <div>
            <label className={"form-label"}>
                {label}
            </label>
            <input value={value} name={name}
                   type={childProps.type ?? "text"}
                   required={childProps.required ?? true}
                   minLength={minLength}
                   className={"form-control"}
                   onChange={handleChange}
                   onFocus={() => setInfoFeedback(showBeforeValidation && !validated ? "info-feedback" : "")}
                   onBlur={() => setInfoFeedback("")}
            />

            {errorMessages?.map((error, index) =>
                <div key={name + "error" + index}
                     className={`invalid-feedback ${infoFeedback}`}>
                    {error}
                </div>
            )}
        </div>
    );
}