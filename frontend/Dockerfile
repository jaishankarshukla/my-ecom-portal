# Use official Node.js image
FROM node:20

# Set working directory inside container
WORKDIR /app

# Copy package files and install dependencies
COPY package*.json ./
RUN npm install

# Copy the rest of your app source code
COPY . .

# Build the app (produces /app/dist)
RUN npm run build

# Install 'serve' globally to serve static files
RUN npm install -g serve

# Expose port 3000 to the host
EXPOSE 3000

# Start the static server, serving the 'dist' directory on port 3000
CMD ["serve", "-s", "dist", "-l", "3000"]