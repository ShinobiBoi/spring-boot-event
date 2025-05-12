import React, { useState, useEffect } from "react";
import Table from "react-bootstrap/Table";
import "../../css/ManageEvents.css";
import { Link, useNavigate } from "react-router-dom";
import Alert from "react-bootstrap/Alert";
import Spinner from "react-bootstrap/Spinner";
import axios from "axios";

const ManageEvents = () => {
    const [events, setEvents] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const navigate = useNavigate();
    const userId = localStorage.getItem("userId");
    const userRole = localStorage.getItem("userRole");

    const fetchEvents = async () => {
        if (userRole !== "Organizer") {
            setLoading(false);
            return;
        }

        try {
            const res = await axios.get(`/event-services/events/user/${userId}`);
            setEvents(res.data);
        } catch (err) {
            console.error(err);
            setError("Failed to load your events.");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        if (userRole !== "Organizer") {
            navigate("/");
            return;
        }
        fetchEvents();
    }, [userId, userRole, navigate]);

    const handleDelete = async (eventId) => {
        try {
            await axios.delete(`/event-services/events/${eventId}`);
            await axios.delete(`/tickets-services/tickets/event/${eventId}`);
            fetchEvents();
        } catch (err) {
            console.error("Failed to delete event:", err);
            setError("Failed to delete event.");
        }
    };

    const handleUpdate = (eventId) => {
        navigate(`/manage-events/update/${eventId}`);
    };

    if (userRole !== "Organizer") {
        return null;
    }

    return (
        <div className="manage-container">
            <div className="manage-events p-5">
                <div className="header d-flex justify-content-between mb-5">
                    <h3 className="text-center">Manage Your Events</h3>
                    <Link to={"add"} className="btn btn-success">
                        Add New Event +
                    </Link>
                </div>

                {loading && (
                    <div className="text-center">
                        <Spinner animation="border" role="status">
                            <span className="visually-hidden">Loading...</span>
                        </Spinner>
                    </div>
                )}

                {error && (
                    <Alert variant="danger" className="p-2" onClose={() => setError("")} dismissible>
                        {error}
                    </Alert>
                )}

                {!loading && !error && events.length === 0 && (
                    <Alert variant="info" className="p-2">
                        You haven't created any events yet. Create your first event!
                    </Alert>
                )}

                {!loading && events.length > 0 && (
                    <Table striped bordered hover>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Image</th>
                                <th>Name</th>
                                <th>Description</th>
                                <th>Date</th>
                                <th>Location</th>
                                <th>Tickets Left</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            {events.map((event) => (
                                <tr key={event.eventId}>
                                    <td>{event.eventId}</td>
                                    <td>
                                        <img
                                            src={event.imageUrl || "https://picsum.photos/200/300"}
                                            alt="Event"
                                            className="image-avatar"
                                        />
                                    </td>
                                    <td>{event.title}</td>
                                    <td>{event.description}</td>
                                    <td>{new Date(event.eventDate).toLocaleDateString()}</td>
                                    <td>{event.venue}</td>
                                    <td>{event.ticketsLeft}</td>
                                    <td>
                                        <button
                                            className="btn btn-sm btn-danger"
                                            onClick={() => handleDelete(event.eventId)}
                                        >
                                            Delete
                                        </button>
                                        <button
                                            className="btn btn-sm btn-primary mx-2"
                                            onClick={() => handleUpdate(event.eventId)}
                                        >
                                            Update
                                        </button>
                                        <Link
                                            to={`/event/${event.eventId}`}
                                            className="btn btn-sm btn-info"
                                        >
                                            View
                                        </Link>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </Table>
                )}
            </div>
        </div>
    );
};

export default ManageEvents;