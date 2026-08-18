# 《Matryoshka Doll（套娃）》整合包

> **Minecraft Forge 1.20.1 整合包** — 一个"层层套娃"的工业化毕业包。
> 玩家从机械动力起家，逐步过渡到魔改格雷科技，最后通过自制 mod 的大型现代机械多方块结构合成毕业物 **MD 之星（MD Star）**。
>
> 名字灵感来源于俄罗斯套娃 — 每个配方都是另一层套娃。

---

## 0. 整合包概览

| 项目 | 内容 |
| --- | --- |
| Minecraft | 1.20.1 |
| 加载器 | Forge 47.3.12 |
| 整合包格式 | CurseForge `manifest.json`（可用 CurseForge App / Prism Launcher 导入） |
| 前期核心 | Create + 一系列 Create 附属 |
| 中期核心 | GregTech CEu Modern（魔改：删除基础工具，保留蒸汽时代及之后；所有配方材料被套娃化） |
| 存储/自动化 | Applied Energistics 2（**配方被特意简化**为友好缓冲区） |
| 探索 | YUNG's Better 系列、Dungeons Arise、Repurposed Structures、Twilight Forest 等 |
| **毕业 mod** | `mdstar`（本仓库 `md-star-mod/` 目录，**自制 Forge mod**） |

---

## 1. 目录结构

```
matryoshka-doll/
├── manifest.json                ← CurseForge 整合包清单（mod 列表）
├── README.md                    ← 本文件
├── overrides/                   ← 实际部署到客户端的"覆盖"目录
│   ├── kubejs/
│   │   ├── startup_scripts/registry.js          ← 自定义物品注册
│   │   ├── server_scripts/
│   │   │   ├── 01_nesting_recipes.js            ← 主套娃配方（非 AE2 全部复杂化）
│   │   │   ├── 02_gregtech_tweaks.js            ← GT 魔改（去基础工具、留蒸汽后）
│   │   │   ├── 03_ae2_simplify.js               ← AE2 配方简化
│   │   │   └── 04_md_star.js                    ← MD 之星毕业链
│   │   └── client_scripts/jei_info.js           ← JEI 提示
│   ├── config/                  ← 各 mod 的配置文件
│   │   ├── dungeons_arise.toml
│   │   ├── yungsbetterdungeons-forge-1_20_1.toml
│   │   ├── repurposed_structures-forge-config.toml
│   │   └── kubejs-common.json
│   ├── server.properties
│   └── mods/                    ← mod jar 放这里（manifest 引导下载）
│
└── md-star-mod/                ← 自制 Forge mod（毕业物 + 大型现代机械多方块）
    ├── build.gradle, gradle.properties, settings.gradle
    └── src/main/
        ├── java/com/matryoshka/mdstar/
        │   ├── MDStarMod.java
        │   ├── registry/                         ← 注册：物品/方块/方块实体/菜单/创造栏
        │   ├── blocks/
        │   │   ├── machinery/MachineCasingBlock.java
        │   │   └── multiblock/                    ← 8 个多方块控制器 + 基类
        │   ├── multiblock/                         ← 多方块结构形状定义
        │   ├── recipe/MDStarAssemblyRecipe.java    ← 自定义配方类型
        │   └── client/                             ← 客户端屏幕/事件
        └── resources/
            ├── assets/mdstar/{blockstates,models,lang}
            ├── data/mdstar/{recipes,loot_tables,advancements}
            └── META-INF/mods.toml, pack.mcmeta
```

---

## 2. 玩家阶段路线图

### 阶段一：机械动力起家（前期）
- 玩家一上来**没有"手敲"工作台**——工作台必须用 Create 齿轮机械组装。
- 安山合金 → 齿轮 → 木轴 → 工作台，整条链要机械动力。
- 机械化红石元件（活塞、观察者等）也要 Create 零件。
- 探索地下城 / 大型建筑，获得前期装备与资源。
- 这阶段目标是把"机械化生产线"建立起来。

### 阶段二：批量流水线（中期）
- 一旦能造出**机械压机 + 搅拌机 + 锯木机**，玩家进入自动化阶段。
- 矿石处理被套娃化：粉碎 → 洗矿 → 熔炼三级流程。
- 此阶段同时启用 **AE2 简化版**——做存储 / 自动化合成，是整合包中相对友好的"减负区"。

### 阶段三：GregTech 蒸汽时代
- 玩家已具备流水线后，开始造 GT 机器。
- GT 的**所有基础工具/武器/装备被移除**——玩家用原版/机械动力的工具。
- 保留**蒸汽锅炉 + 蒸汽机 + 青铜机器 + LV+**。
- 所有 GT 机器配方被套娃化：必须同时有 GT 板/线 + Create 齿轮 + AE2 处理器才能造出来。

### 阶段四：现代大型机械多方块（毕业阶段）
进入自制 mod `mdstar` 的内容：

| 多方块结构 | 尺寸 | 用途 |
| --- | --- | --- |
| MD 组装塔 | 5×5×5 | 用 8 个 mod 精华合成 MD 之心 |
| MD 聚变反应堆 | 5×5×5 | 把等离子核心+太阳尘 → 戴森框架，并提供 FE |
| 超导线圈矩阵 | 3×3×3 | 高压输电中继（50M FE 缓冲） |
| 量子纠缠腔 | 5×5×5 | 4× AE2 量子链接室 → 量子电路 |
| 反物质催化炉 | 5×5×5 | 反物质颗粒+太阳尘 → 等离子核心 |
| 戴森球核心 | 7×7×7 | 戴森框架+量子电路 → 反物质颗粒；持续产能 |
| 地壳能量开采站 | 5×5×5 | 自动产出矿石（无需挖矿） |
| **MD 之星合成阵列** | 7×7×7 | **最终毕业**：MD 之星壳 + 8 量子电路 + MD 之心 → MD 之星 |

### 阶段五：毕业物 MD 之星
MD 之星的合成路径：

```
┌─ Create 精华  ──┐
│ Rails 精华      │
│ Slice 精华      │
│ CnA 精华        │     ┌────────────────┐     ┌──────────────┐     ┌──────────────┐
│ GregTech 精华  ├────►│ MD 框架 + 9 精华 ├────►│   MD 之心    ├────►│ MD 之星壳    │
│ AE2 精华        │     └────────────────┘     └──────────────┘     └──────┬───────┘
│ AE2 Things 精华 │                                                       │
└ Twilight 精华 ─┘                                                       ▼
                                            ┌──────────────────┐     ┌──────────────┐
                          探索精华 ────────►│ MD 之星合成阵列  ├────►│   MD 之星    │ ★ 毕业物 ★
                                            │ (8× 量子电路)    │     └──────────────┘
                                            └──────────────────┘
```

每个"精华"都来自不同 mod 的终末产物，**玩家必须把整合包里所有 mod 都玩一遍**才能凑齐 9 个精华。

---

## 3. 自制 mod `mdstar` 概览

`md-star-mod/` 是一个完整的 Forge 1.20.1 Gradle 项目：

- **`MDStarMod.java`** 主类，注册所有 deferred registers。
- **8 个多方块控制器**（`blocks/multiblock/`）：每个继承 `MultiblockControllerBlock`，自带多方块校验 + ticker。
- **多方块校验框架**（`multiblock/`）：`MultiblockPattern` 描述形状，`MultiblockPatterns` 集中所有结构定义。
- **`MDStarAssemblyRecipe`**：自定义数据驱动配方类型（`mdstar:md_assembly`），让玩家可在 MD 组装塔里跑 JSON 配方。
- **GUI/Menu/Screen**：MD 组装塔的 8 输入 + 1 输出 + 玩家背包 GUI。
- **资源**：blockstates / models / lang / loot tables / recipes / tags 全套。

### 构建

```bash
cd md-star-mod
./gradlew build
# 输出：build/libs/mdstar-0.1.0-alpha.jar
# 拷贝到整合包 overrides/mods/ 即可
```

**已验证可用的工具链组合**：
- ForgeGradle `6.0.16`
- Gradle Wrapper `8.1.1`（首次运行会自动下载）
- JDK `17.0.2`（`JAVA_HOME` 必须指向 17，避免使用 JDK 25）
- Forge `1.20.1-47.3.12`

> **依赖说明**：`mdstar` 源码不直接引用 Create / GregTech / AE2 / JEI 的 Java API——
> 所有跨 mod 的合成与联动都在 KubeJS 脚本与 `data/mdstar/recipes/*.json` 数据包里完成。
> 因此 `build.gradle` 的 `dependencies {}` 块只声明 Forge，无需拉取 mod 依赖即可编译。

---

## 4. 部署与启动

### 用 CurseForge App / Prism Launcher 导入

1. 把整个 `matryoshka-doll/` 目录打包成 zip（包含 `manifest.json` 和 `overrides/`）。
2. 在 CurseForge / Prism 中"导入整合包"，选 zip。
3. 启动器会按 manifest 自动下载所有 mod（**注意：manifest 中 `fileID` 为 0 的项需要你到 CurseForge 网页取最新文件 ID 填入**——见下文）。
4. 编译并放入 `mdstar-*.jar` 到 `overrides/mods/` 后启动游戏。

### 填补 CurseForge 文件 ID

`manifest.json` 中所有 `fileID: 0` 都需要替换成真实的最新文件 ID：

1. 打开 https://www.curseforge.com/minecraft/mc-mods/<mod-slug>/files/all
2. 选择 1.20.1 + Forge 的最新版，复制 URL 末尾的数字（即 fileID）。
3. 替换 `manifest.json` 中对应项的 `"fileID": 0`。

> 我没有联网获取所有 fileID 是因为这数字会随 mod 更新变化——请按上法手动填。

---

## 5. 已知局限 / 后续 TODO

- **mod jar 文件**：所有 mod 都需要从 CurseForge 下载（manifest 引导）；**自制 `mdstar-0.1.0-alpha.jar` 已构建并放入 `overrides/mods/`**。
- **mdstar mod 贴图**：当前使用 minecraft 内置方块/物品贴图作为占位，建议后续用 SDXL 生成正式贴图。
- **mdstar 多方块渲染**：当前用普通方块模型；可加 BlockEntityRenderer 实现旋转/发光/动画。
- **KubeJS 配方覆盖度**：脚本已示范主要套娃配方，但整合包实际部署后建议对每个 mod 的核心产物再补充配方（脚本结构已预留）。
- **`mdstar` BlockEntity 的能量系统**：当前用 `energyStored` 字段简化实现，未来可接 Forge Energy Capability 实现真正的能量网络。
- **MD 组装塔配方**：当前每个 essence 的获取都来自不同 mod 的产物；具体 mod 物品 ID（如 `createaddition:alternator`）需要根据实际安装的 mod 版本校准。
- **`mods.toml` 依赖**：Create / GregTech / AE2 / JEI / KubeJS 都标记为 `mandatory=false`，因 `mdstar` 代码不直接引用它们的 API；游戏运行时这些 mod 由整合包 manifest 提供即可。

---

## 5.1 构建状态

| 组件 | 状态 |
| --- | --- |
| `manifest.json` + 70+ mods 列表 | ✅ 完成 |
| KubeJS 启动/服务端/客户端脚本（4 个 server + 1 startup + 1 client） | ✅ 完成 |
| YUNG / Dungeons Arise / Repurposed Structures 探索 mod 配置 | ✅ 完成 |
| `md-star-mod` Java 源码（8 个多方块 + 组装塔 GUI + 自定义配方类型） | ✅ 完成 |
| `md-star-mod` 资源包（blockstates / models / lang / loot / recipes / tags） | ✅ 完成 |
| **`mdstar-0.1.0-alpha.jar` 编译产物** | ✅ **已构建**（`overrides/mods/mdstar-0.1.0-alpha.jar`，82 KB） |

---

## 6. 关键设计理念回顾

| 玩家需求 | 实现方式 |
| --- | --- |
| 前期机械动力 + 附属为主 | manifest 收录 25+ Create 附属 |
| 进入批量生产流水线 | 1×4 KubeJS 脚本强制所有机器走 Create 机械流程 |
| GregTech 魔改（去基础工具，留蒸汽后，配方变复杂） | `02_gregtech_tweaks.js`：删除 GT 工具/装备/原始机器；所有 LV+ 机器要 Create 齿轮 + GT 板/线 + 红石 |
| 自制 mod 大型现代机械多方块用于合成 MD之星 | `md-star-mod/`：8 个多方块 + MD 组装塔配方系统 |
| MD之星材料"全面"，强制玩所有 mod | `04_md_star.js`：9 个 essence 分别来自 Create / 附属 / GT / AE2 / AE2 Things / Twilight / 探索 mod |
| 整合包加 AE2，但 AE2 配方简化 | `03_ae2_simplify.js`：处理器/控制器/存储元件/缆线全部走"红石 + 玻璃 + 1 处理器"的简化路径 |
| 除 AE2 外其他 mod 配方套娃化 | `01_nesting_recipes.js`：工作台/工具/机器/红石元件/铁砧全部要机械动力 + 多级零件 |
| 加入大量建筑让玩家探索 | YUNG's Better 系列、Dungeons Arise、Repurposed Structures、Towns and Towers、Twilight Forest、Choice's Overhauled Village、Explorify、Dungeons and Taverns |
| 让原版建筑没那么无聊 | 全套 YUNG + Repurposed Structures + Dungeons Arise 重做原版建筑生成 |
| 整合包名字叫《matryoshka doll》或《套娃》 | manifest.json 中 `"name": "Matryoshka Doll (套娃)"` |

---

## 7. 联系与许可

- 整合包：仅供学习交流
- 自制 mod `mdstar`：MIT 许可（见 `md-star-mod/src/main/resources/META-INF/mods.toml`）

> "层层套娃" — 当你拆开一层配方，里面还有另一层配方在等你。
