const url = "http://localhost:8080";

export const CREATE = "create";
export const READ = "get";
export const UPDATE = "update";
export const DELETE = "delete";
export const CONFIRM = "confirm-registration";

export enum Entity {
    CATEGORY = "category",
    PRODUCT = "product",
    USER = "user"
}

export enum Path {
    CREATE = "create",
    READ = "get",
    UPDATE = "update",
    DELETE = "delete",
    CONFIRM = "confirm-registration"
}

export enum Method {
    GET = "GET",
    POST = "POST",
    PUT = "PUT",
    DELETE = "DELETE"
}

export const getInit: { [key: string]: any } = {
    method: "GET",
    headers: {
        "Content-Type": "application/json",
    },
    credentials: "include",
};

async function fetchData(path: string, init = getInit) {
    const response = await fetch(url + path, init);
    const json = response.status === 204 ? undefined : await response.json();
    return {
        body: json,
        status: response.status,
        ok: response.ok
    };
}

export async function fetchImage(path: string, headers?: ResponseInit, method?: Method, body?: Record<string, any>) {
    const response = await fetch(url + path, {
        ...getInit
    })
    const blob = await response.blob();
    const s = URL.createObjectURL(blob);
}

export async function fetching(path: string, headers?: ResponseInit, method?: Method, body?: Record<string, any>) {
    const response = await fetch(url + path, {
        ...getInit,
        method,
        ...headers,
        body: JSON.stringify(body),
    });
    const json = response.status === 204 ? undefined : await response.json();
    return {
        body: json,
        status: response.status,
        ok: response.ok
    };
}

export async function confirm(entity: Entity, token: string) {
    return await fetchData(`/${entity}/${CONFIRM}?token=${token}`);
}

export async function login(body: Record<string, any>) {
    return await fetchData("/login",
        {
            ...getInit,
            credentials: "include",
            method: "POST",
            body: JSON.stringify(body),
        });
}

export async function createEntity(body: { [key: string]: any }, entity: Entity) {
    return await fetchData(`/${entity}/${CREATE}`,
        {
            ...getInit,
            body: JSON.stringify(body),
            method: "POST"
        });
}

export async function updateEntity(body: { [key: string]: any }, entity: Entity, id: Number) {
    return await fetchData(`/${entity}/${UPDATE}/${id}`,
        {
            ...getInit,
            body: JSON.stringify(body),
            method: "PUT"
        });
}

export async function deleteEntity(entity: Entity, id: Number) {
    return await fetchData(`/${entity}/${DELETE}/${id}`,
        {
            ...getInit,
            method: "DELETE"
        });
}


export function normalize(text: string) {
    return text.split(" ").map((t, i) => {
        t = t.toLowerCase();
        if (i !== 0) {
            return t.charAt(0).toLowerCase() + t.slice(1);
        }
        return t;
    }).join()
        .normalize("NFKC")
        .replaceAll("\\p{M}", "")
        .replaceAll("[\\u0141-\\u0142]", "l");
}