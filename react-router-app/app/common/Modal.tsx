import React from 'react';

export default function Modal({header, body, footerButton, handleClick}: {
    header: string,
    body: string,
    footerButton: string,
    handleClick: () => void
}) {
    return (
        <div className="modal fade" id="modalSheet" data-bs-backdrop="static" data-bs-keyboard="false"
             tabIndex={-1}>
            <div className="modal-dialog modal-dialog-centered">
                <div className="modal-content rounded-4 shadow">
                    <div className="modal-header">
                        <h1 className="modal-title fs-5" id="exampleModalLabel">{header}</h1>
                    </div>
                    <div className="modal-body">
                        {body}
                    </div>
                    <div className="modal-footer">
                        <button type="button" className="btn btn-lg btn-primary" data-bs-dismiss="modal"
                                onClick={handleClick}>
                            {footerButton}
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}