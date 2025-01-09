import {useState} from 'react';

export const useInput = (initialState) => {
    const [state, setState] = useState(initialState);
    const input = {};
    for (const key of Object.keys(initialState)) {
        const name = key;
        input[name] = {
            name,
            value: state[name],
            onChange: e => setState(prevState => {
                return {
                    ...prevState,
                    [name]: e.target.value
                };
            })
        };
    }
    return [state, input];
};