import React, {useEffect, useState} from 'react';
import type {Route} from "../../.react-router/types/app/routes/+types/logout";
import {Entity, fetching} from "~/data";

export async function loader({request}: Route.LoaderArgs) {
    const response = await fetching(`/${Entity.PRODUCT}`);
}

export default function Products({loaderData}: Route.ComponentProps) {
    const [imageUrl, setImageUrl] = useState("");

    useEffect(() => {
        fetch(`http://localhost:8080/image/1`)
            .then((response) => response.blob())
            .then((blob) => {
                const objectUrl = URL.createObjectURL(blob);
                setImageUrl(objectUrl);
            })
            .catch((error) => console.error("Error loading image:", error));
    }, []);

    return <img src={"blob:http://localhost:5173/2befbebc-5f58-4353-b5ca-346791abb52d"} alt="Product"/>;
}