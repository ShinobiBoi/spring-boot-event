import React, { useState } from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Alert from "react-bootstrap/Alert";
import "../../css/Login.css";

const AddEvent = () => {
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");
    const [isSubmitting, setIsSubmitting] = useState(false);
    const organizerName = localStorage.getItem("userName");

    // Get organizer name from localStorage


    const handleSubmit = async (e) => {
        e.preventDefault();
        const form = e.target;
        const formData = new FormData(form);

        // Convert FormData to a plain object
        const userId = localStorage.getItem("userId");
        const body = Object.fromEntries(formData.entries());

        // Include organizerName from localStorage and remove the form field value
        const data = {
            ...body,
            organizerName, // Use the value from localStorage
            submitted: true,
            userId
        };

        console.log('Form data:', data);

        try {
            setIsSubmitting(true);
            setError("");
            setSuccess("");

            const token = localStorage.getItem('token');
            const response = await fetch("/event-services/events", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(data)
            });

            const responseData = await response.text();

            if (responseData.startsWith('<!DOCTYPE html>')) {
                throw new Error("Authentication required or server error");
            }

            let result;
            try {
                result = JSON.parse(responseData);
            } catch (e) {
                throw new Error("Invalid server response");
            }

            if (!response.ok) {
                throw new Error(result.message || `Server error: ${response.status}`);
            }

            setSuccess("Event added successfully!");
            form.reset();

        } catch (err) {
            console.error("Submission error:", err);
            setError(err.message || "Failed to add event. Please try again.");
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <div className="login-container">
            <h1>Add New Event Form</h1>

            {error && <Alert variant="danger" className="p-2">{error}</Alert>}
            {success && <Alert variant="success" className="p-2">{success}</Alert>}

            <Form onSubmit={handleSubmit}>
                <Form.Group className="mb-3">
                    <Form.Label>Organizer Name</Form.Label>
                    <Form.Control
                        type="text"
                        value={organizerName}
                        readOnly
                        plaintext
                        className="form-control-plaintext"
                    />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Event Title *</Form.Label>
                    <Form.Control
                        type="text"
                        name="title"
                        required
                        placeholder="Enter event title"
                        disabled={isSubmitting}
                    />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Description</Form.Label>
                    <Form.Control
                        as="textarea"
                        name="description"
                        placeholder="Enter event description"
                        rows={4}
                        disabled={isSubmitting}
                    />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Venue *</Form.Label>
                    <Form.Control
                        type="text"
                        name="venue"
                        required
                        placeholder="Enter venue location"
                        disabled={isSubmitting}
                    />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Event Date and Time *</Form.Label>
                    <Form.Control
                        type="datetime-local"
                        name="eventDate"
                        required
                        disabled={isSubmitting}
                    />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Ticket Price *</Form.Label>
                    <Form.Control
                        type="number"
                        name="ticketPrice"
                        required
                        placeholder="0.00"
                        min="0"
                        step="0.01"
                        disabled={isSubmitting}
                    />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Available Tickets *</Form.Label>
                    <Form.Control
                        type="number"
                        name="ticketsLeft"
                        required
                        placeholder="0"
                        min="0"
                        disabled={isSubmitting}
                    />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Initial Participants Count</Form.Label>
                    <Form.Control
                        type="number"
                        name="participantsSubmitted"
                        placeholder="0"
                        min="0"
                        disabled={isSubmitting}
                    />
                </Form.Group>

                <Button
                    className="btn btn-green w-100"
                    variant="primary"
                    type="submit"
                    disabled={isSubmitting}
                >
                    {isSubmitting ? 'Adding Event...' : 'Add New Event'}
                </Button>
            </Form>
            <p className="text-muted mt-2">* indicates required field</p>
        </div>
    );
};

export default AddEvent;