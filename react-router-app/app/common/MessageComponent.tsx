import React, {type JSX} from 'react';

export type MessageComponentProps = {
    message?: string | JSX.Element;
    details?: string | JSX.Element;
    stack?: string | JSX.Element;
}

export default function MessageComponent({message, details, stack}: MessageComponentProps) {
    message = message ?? "Oops!";
    details = details ?? "An unexpected error occurred.";
    return (
        <main className="pt-16 p-4 container mx-auto">
            <h1>{message}</h1>
            <p>{details}</p>
            {stack && (
                <pre className="w-full p-4 overflow-x-auto">
          <code>{stack}</code>
        </pre>
            )}
        </main>
    );
}
