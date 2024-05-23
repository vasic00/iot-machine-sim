const WebSocket = require('ws');
const amqp = require('amqplib');

const wss = new WebSocket.Server({ port: 8084 });

const rabbitMQUrl = 'amqp://localhost';
const exchangeName = 'exchange_name';
const queueName = 'queue_name';

// Function to publish messages to RabbitMQ
async function publishMessage(message) {
    try {
        console.log(message);
        const connection = await amqp.connect(rabbitMQUrl);
        const channel = await connection.createChannel();
        await channel.assertExchange(exchangeName, 'direct', { durable: true });
        await channel.assertQueue(queueName, { durable: true });
        // channel.publish(exchangeName, 'routing_key', Buffer.from(message));
        channel.sendToQueue(queueName, Buffer.from(message));
        console.log('Message published to RabbitMQ:', message);
        await channel.close();
        await connection.close();
    } catch (error) {
        console.error('Error publishing message to RabbitMQ:', error);
    }
}

// Event listener for WebSocket connections
wss.on('connection', (ws) => {

    console.log('A new client connected');

    ws.on('message', async (message) => {
        console.log('Received message from client:', message);
        // Publish the received message to RabbitMQ
        await publishMessage(message.toString('utf-8'));
    });

    ws.on('close', () => {
        console.log('Client disconnected');
    });
});

console.log('WebSocket server running on port 8084');