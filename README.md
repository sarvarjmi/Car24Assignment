# Cars24 SDUI Framework

A production-grade, highly extensible **Server-Driven UI (SDUI)** framework built for the Cars24 Android Assignment. This project demonstrates an advanced implementation of dynamic UI rendering using **Clean Architecture**, **MVI**, and **Jetpack Compose**.

## 📺 Project Walkthrough (Placeholder)
> [!NOTE]
> [Sample Video Walkthrough Link](https://drive.google.com/file/d/15SwV_cYFh3obsPmEl2CV75ndjkSf6WoI/view?usp=sharing) (To be updated)

---

## 🏛 Architecture & Design Patterns

The project follows a **Multi-Module Clean Architecture** approach to ensure strict separation of concerns and scalability.

- **MVI (Model-View-Intent)**: Ensures a unidirectional data flow. The UI emits intents (user actions), the ViewModel processes them into states, and the UI re-renders based on the single source of truth.
- **Single Source of Truth (SSOT)**: A local Room database acts as the server. All screens fetch data from Room via Kotlin Flows, enabling real-time UI updates when the database changes.
- **Polymorphic Serialization**: Leverages `kotlinx.serialization` to handle complex, nested component hierarchies with custom discriminators (`type`).

---

## 🚀 Key Features

1.  **Generic Rendering Engine**: A recursive engine that transforms any JSON hierarchy into a performant Compose UI.
2.  **Local SDUI Server (CMS)**: A dedicated in-app panel to edit, validate, and save raw JSON payloads directly to the local database.
3.  **Real-Time Reactive UI**: Changes made in the Server Panel trigger instant re-renders across the entire app without a restart.
4.  **Robust Validation**: Strict schema enforcement, circular reference protection, and duplicate ID detection.
5.  **Token-Based Design System**: Centralized mapping of design tokens (Colors, Spacing, Typography) to Material 3.
6.  **Premium UX**: Staggered entrance animations for all components and a "Discard Changes" safety system.

---

## 🚦 How to Start & Run

### 1. Prerequisites
- Android Studio **Ladybug** or newer.
- Android SDK **35**.
- Minimum SDK **26**.

### 2. Initial Setup
1.  **Clone & Sync**: Open the project and sync the Gradle files.
2.  **Launch**: Run the `:app` module on an emulator or physical device.
3.  **Automatic Seeding**: On the first launch (or version bump), the app detects an empty database and automatically seeds it from the rich JSON assets found in `app/src/main/assets/`.

### 3. Usage Flow
- **Entry Screen**: Choose between the "Landing Page" (Main App) and the "SDUI Server" (CMS).
- **Navigation**: Use the 4-tab bottom navigation (Home, Deals, Profile, Welcome) which are all 100% data-driven.
- **Live Evolution**: Go to the Server Panel, pick a screen, edit its JSON (e.g., change a car price), and click "Update" to see the live change.

---

## 🧩 Widget Catalog & JSON Schema

Every widget requires a unique `id` and a `type` discriminator. Below are the primary widgets and their required properties.

### 1. Text Widget (`type: "text"`)
Renders localized and themed text.
- **Required**: `properties.text`
- **Optional**: `properties.typography` (TITLE_LARGE, BODY_MEDIUM, etc.), `properties.color` (PRIMARY, ERROR, etc.)
```json
{
  "id": "header_title",
  "type": "text",
  "properties": { "text": "Featured Cars", "typography": "TITLE_MEDIUM", "color": "PRIMARY" }
}
```

### 2. Image Widget (`type: "image"`)
Displays remote or local drawable resources.
- **Required**: `properties.url`
- **Optional**: `style.width`, `style.height`, `style.shape`
```json
{
  "id": "car_img",
  "type": "image",
  "style": { "width": "100dp", "height": "75dp", "shape": "SMALL" },
  "properties": { "url": "ic_car_placeholder" }
}
```

### 3. Car Card (`type: "car_card"`)
A complex business widget representing a car listing.
- **Required**: `properties.imageUrl`, `properties.title`, `properties.price`, `properties.location`
- **Optional**: `properties.badges` (List), `properties.fuel`, `properties.transmission`
```json
{
  "id": "car_1",
  "type": "car_card",
  "properties": {
    "imageUrl": "ic_car_placeholder",
    "title": "Maruti Swift 2021",
    "price": "₹6.15 Lakh",
    "location": "New Delhi",
    "badges": ["CERTIFIED"]
  }
}
```

### 4. Grid Widget (`type: "grid"`)
Creates a multi-column structured layout.
- **Required**: `properties.columns` (Int), `children` (List)
```json
{
  "id": "promo_grid",
  "type": "grid",
  "properties": { "columns": 2 },
  "children": [ ... ]
}
```

### 5. Hero Banner (`type: "hero_banner"`)
Full-width promotional header with CTA.
- **Required**: `properties.imageUrl`, `properties.title`, `properties.subtitle`, `properties.ctaText`
- **Optional**: `actions.click`
```json
{
  "id": "hero_main",
  "type": "hero_banner",
  "properties": {
    "imageUrl": "ic_landing_hero",
    "title": "Drive Your Dreams",
    "ctaText": "Get Started"
  },
  "actions": { "click": { "type": "navigate", "target": "home_screen_route" } }
}
```

### Other Supported Widgets:
- `Banner`: Medium-sized promotional cards.
- `ChipGroup` & `Chip`: Dynamic filtering and selection logic.
- `Divider` & `Spacer`: Visual separators using design tokens.
- `Icon`: Vector graphic support with tint mapping.
- `Badge`: Status and indicator tags.
- `Button` & `CTA`: Action triggers.
- `LazyRow` & `LazyColumn`: Performant horizontal and vertical lists.

---

## 🏠 Home Page JSON Example

Below is a snippet of the `home.json` which drives the main dashboard. It includes a search bar, category chips, and a horizontal rail of cars.

```json
{
  "metadata": {
    "id": "home_screen",
    "name": "CARS24",
    "schemaVersion": "1.4.0",
    "rendererVersion": "1.0.0"
  },
  "configuration": { "refreshable": true, "scrollable": true, "safeArea": true },
  "layout": { "type": "LazyColumn" },
  "sections": [
    {
      "id": "nav_section",
      "type": "navigation",
      "order": 1,
      "components": [
        { 
          "id": "h_search", 
          "type": "search_bar", 
          "style": { "padding": { "horizontal": "MEDIUM", "bottom": "MEDIUM" } }, 
          "properties": { "placeholder": "Search by make, model or city..." } 
        }
      ]
    },
    {
      "id": "featured_rail",
      "type": "featured",
      "order": 4,
      "components": [
        { 
          "id": "f_rail", 
          "type": "lazy_row", 
          "style": { "padding": { "start": "MEDIUM" } },
          "children": [
            {
              "id": "fc1", 
              "type": "car_card", 
              "properties": { 
                "imageUrl": "ic_car_placeholder", 
                "title": "Mahindra Thar 2023", 
                "price": "₹14.80 L", 
                "location": "Gurgaon", 
                "badges": ["NEW"], 
                "fuel": "Diesel" 
              }
            }
          ]
        }
      ]
    }
  ]
}
```

---

## 🧪 Testing

Run all tests via terminal:
```bash
./gradlew test
```
- **Unit Tests**: Found in `:core-json` (Parser tests) and `:core-database` (Repository & Mapper logic).
- **UI Tests**: Found in `:feature-home` and `:feature-server` for Composable verification.

---

## 📦 Project Structure

- `:app`: Startup, Seeding logic, and Parameterized NavHost.
- `:core:core-designsystem`: Spacing, Color, and Typography resolvers.
- `:core:core-renderer`: Recursive rendering engine and action dispatcher.
- `:core:core-json`: Polymorphic serialization and strict validator.
- `:core:core-database`: Room SSOT, DAOs, and Initial Seeder.
- `:feature:feature-server`: Native CMS editor with Undo/Redo.
- `:feature:feature-home`: Dynamic Dashboard with Pull-to-Refresh.

---
Developed for the **Cars24 Mobile Engineering Assignment**.
