import {type RouteConfig, layout, index, route} from "@react-router/dev/routes";

export default [
    layout("layouts/layout.tsx", [
        index("routes/home.tsx"),
        route("login", "routes/login.tsx"),
        route("logout", "routes/logout.tsx"),
        route("registration", "routes/registration.tsx"),
        route("products", "routes/products.tsx"),
    ]),
    route("registration-confirm", "routes/registration-confirm.tsx")
] satisfies RouteConfig;
