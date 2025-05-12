import { createBrowserRouter, Navigate } from "react-router-dom";
import Home from "./pages/home/Home";
import Login from "./pages/Auth/Login";
import Register from "./pages/Auth/Register";
import App from "./App";
import ManageEvents from "./pages/manage-events/ManageEvents";
import AddEvent from "./pages/manage-events/AddEvent";
import UpdateEvent from "./pages/manage-events/UpdateEvent";
import Guest from "./middleware/Guest";
import Admin from "./middleware/Admin";
import MyTickets from "./pages/manage-events/MyTickets";
import EventRequests from "./pages/manage-events/EventRequests";
import ApproveOrganizer from "./pages/manage-events/ApproveOrganizer";
import SavedEvents from './pages/SavedEvents';
import EventDetails from './pages/EventDetails'; 

export const routes = createBrowserRouter([
    {
        path: "",
        element: <App />,
        children: [
            {
                path: "/",
                element: <Home />,
            },
            {
                path: "/saved-events",
                element: <SavedEvents />,
            },
            {
                path: "/event/:id", 
                element: <EventDetails />,
            },

            // GUEST MIDDLEWARE
            {
                element: <Guest />,
                children: [
                    {
                        path: "/login",
                        element: <Login />,
                    },
                    {
                        path: "/register",
                        element: <Register />,
                    },
                ],
            },
            {
                path: "/manage-events",
                // element: <Admin />,
                children: [
                    {
                        path: "",
                        element: <ManageEvents />,
                    },
                    {
                        path: "add",
                        element: <AddEvent />,
                    },
                    {
                        path: "update/:eventId",
                        element: <UpdateEvent />
                    },
                    {
                        path: "my-tickets",
                        element: <MyTickets />,
                    },
                    {
                        path: "requests",
                        element: <EventRequests />,
                    },
                    {
                        path: "approve-organizer",
                        element: <ApproveOrganizer />,
                    },
                ],
            },
        ],
    },
    {
        path: "*",
        element: <Navigate to={"/"} />,
    },
]);
