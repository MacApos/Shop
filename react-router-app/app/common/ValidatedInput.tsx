import React from 'react';

interface ValidatedInputProps {
    label?: string;
    errors?: string[];
    children: React.ReactElement<HTMLInputElement>;
}

export default function ValidatedInput(props: ValidatedInputProps) {
    const {children} = props;
    const childProps = children.props;
    const name = childProps.name;

    let {label, errors} = props;
    label = label ?? name.charAt(0).toUpperCase() + name.slice(1).toLowerCase();
    errors = errors ?? [`Please provide a valid ${label.toLowerCase()}.`];

    const {type, required, minLength, className, ...otherProps} = childProps;
    return (
        <div>
            <label className="form-label">
                {label}
            </label>
            <input name={name}
                   type={childProps.type ?? "text"}
                   required={childProps.required ?? true}
                   minLength={childProps.minLength ?? 3}
                   className={"form-control"}
                   defaultValue={childProps.defaultValue ?? ""}
            />
            {errors.map((error, index) =>
                <div key={"error" + index} className="invalid-feedback">
                    {error}
                </div>
            )}
        </div>
    );
}