# Stage 1: Build the Angular application
FROM node:20.12.1 as build  
WORKDIR /app  
COPY package.json package-lock.json ./ 
RUN npm install  
COPY . . 
RUN npx ng build

# Stage 2: Serve the application with Node.js
FROM node:20.12.1 as serve  
WORKDIR /app  
COPY --from=build /app/dist ./dist  
EXPOSE 4200 
CMD ["node", "dist/frontend/server/server.mjs"] 