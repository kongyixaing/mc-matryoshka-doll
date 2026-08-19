# Matryoshka Doll 模组批量下载脚本
# 适用于 Windows PowerShell
# 用途：从 CurseForge / Modrinth 下载所有需要的模组

param(
    [string]$OutputDir = ".\mods"
)

$ErrorActionPreference = "Continue"

# 创建输出目录
if (-not (Test-Path $OutputDir)) {
    New-Item -ItemType Directory -Path $OutputDir -Force | Out-Null
}

Write-Host "=== Matryoshka Doll (套娃) 模组批量下载 ===" -ForegroundColor Cyan
Write-Host "输出目录: $OutputDir" -ForegroundColor Yellow
Write-Host ""

# 模组列表 - 使用 Modrinth API (更稳定)
$modrinthMods = @(
    @{
        name = "KubeJS"; 
        version = "2001.6.5-build.26"; 
        url = "https://cdn.modrinth.com/data/kubejs/versions/forge-2001.6.5-build.26/file.jar";
        filename = "kubejs-forge-2001.6.5-build.26.jar"
    },
    @{
        name = "Architectury API"; 
        version = "9.2.14"; 
        url = "https://cdn.modrinth.com/data/architectury-api/versions/forge-9.2.14/file.jar";
        filename = "architectury-api-9.2.14-forge.jar"
    },
    @{
        name = "Cloth Config"; 
        version = "11.1.136"; 
        url = "https://cdn.modrinth.com/data/cloth-config/versions/forge-11.1.136/file.jar";
        filename = "cloth-config-11.1.136-forge.jar"
    },
    @{
        name = "Curios API"; 
        version = "5.14.1"; 
        url = "https://cdn.modrinth.com/data/curios/versions/forge-5.14.1/file.jar";
        filename = "curios-forge-5.14.1+1.20.1.jar"
    },
    @{
        name = "Create"; 
        version = "1.20.1-6.0.8"; 
        url = "https://cdn.modrinth.com/data/create/versions/1.20.1-6.0.8/file.jar";
        filename = "create-1.20.1-6.0.8.jar"
    },
    @{
        name = "Applied Energistics 2"; 
        version = "15.4.10"; 
        url = "https://cdn.modrinth.com/data/applied-energistics-2/versions/15.4.10/file.jar";
        filename = "appliedenergistics2-forge-15.4.10.jar"
    },
    @{
        name = "GregTech CEu Modern"; 
        version = "1.20.1-7.5.3"; 
        url = "https://cdn.modrinth.com/data/gregtechceu-modern/versions/mc1.20.1-7.5.3-forge/file.jar";
        filename = "gtceu-1.20.1-7.5.3.jar"
    },
    @{
        name = "JEI"; 
        version = "15.49.0"; 
        url = "https://cdn.modrinth.com/data/jei/versions/15.49.0.191/file.jar";
        filename = "jei-1.20.1-forge-15.49.0.191.jar"
    },
    @{
        name = "Jade"; 
        version = "11.13.3"; 
        url = "https://cdn.modrinth.com/data/jade/versions/forge-11.13.3/file.jar";
        filename = "jade-11.13.3-forge.jar"
    },
    @{
        name = "JourneyMap"; 
        version = "1.20.1-6.0.2"; 
        url = "https://cdn.modrinth.com/data/journeymap/versions/1.20.1-6.0.2/file.jar";
        filename = "journeymap-1.20.1-6.0.2.jar"
    },
    @{
        name = "FTB Quests"; 
        version = "2001.4.9"; 
        url = "https://cdn.modrinth.com/data/ftb-quests/versions/2001.4.9/file.jar";
        filename = "ftb-quests-2001.4.9.jar"
    },
    @{
        name = "FTB Library"; 
        version = "2001.2.9"; 
        url = "https://cdn.modrinth.com/data/ftb-library/versions/2001.2.9/file.jar";
        filename = "ftb-library-2001.2.9.jar"
    },
    @{
        name = "FTB Teams"; 
        version = "2001.3.2"; 
        url = "https://cdn.modrinth.com/data/ftb-teams/versions/2001.3.2/file.jar";
        filename = "ftb-teams-2001.3.2.jar"
    },
    @{
        name = "Yung's Better Dungeons"; 
        version = "4.0.4"; 
        url = "https://cdn.modrinth.com/data/yungs-better-dungeons/versions/1.20-forge-4.0.4/file.jar";
        filename = "yungs-better-dungeons-4.0.4-forge.jar"
    },
    @{
        name = "Yung's Better Mineshafts"; 
        version = "4.0.4"; 
        url = "https://cdn.modrinth.com/data/yungs-better-mineshafts/versions/1.20-forge-4.0.4/file.jar";
        filename = "yungs-better-mineshafts-4.0.4-forge.jar"
    },
    @{
        name = "Yung's Better Strongholds"; 
        version = "4.0.3"; 
        url = "https://cdn.modrinth.com/data/yungs-better-strongholds/versions/1.20-forge-4.0.3/file.jar";
        filename = "yungs-better-strongholds-4.0.3-forge.jar"
    },
    @{
        name = "Dungeons Arise"; 
        version = "2.1.58"; 
        url = "https://cdn.modrinth.com/data/dungeons-arise/versions/1.20.x-2.1.58/file.jar";
        filename = "dungeons-arise-1.20.x-2.1.58.jar"
    },
    @{
        name = "Repurposed Structures"; 
        version = "7.1.24"; 
        url = "https://cdn.modrinth.com/data/repurposed-structures/versions/7.1.24+1.20.1/file.jar";
        filename = "repurposed-structures-7.1.24.jar"
    },
    @{
        name = "Macaw's Bridges"; 
        version = "3.1.2"; 
        url = "https://cdn.modrinth.com/data/macaws-bridges/versions/3.1.2/file.jar";
        filename = "macaws-bridges-3.1.2.jar"
    },
    @{
        name = "Macaw's Doors"; 
        version = "1.1.5"; 
        url = "https://cdn.modrinth.com/data/macaws-doors/versions/1.1.5/file.jar";
        filename = "macaws-doors-1.1.5.jar"
    },
    @{
        name = "Quark"; 
        version = "4.0-462"; 
        url = "https://cdn.modrinth.com/data/quark/versions/4.0-462/file.jar";
        filename = "quark-4.0-462.jar"
    },
    @{
        name = "Supplementaries"; 
        version = "3.1.43"; 
        url = "https://cdn.modrinth.com/data/supplementaries/versions/1.20-3.1.43/file.jar";
        filename = "supplementaries-1.20-3.1.43.jar"
    },
    @{
        name = "Rhino"; 
        version = "2001.2.3"; 
        url = "https://cdn.modrinth.com/data/rhino/versions/2001.2.3-build.10/file.jar";
        filename = "rhino-2001.2.3-build.10.jar"
    }
)

# 需要手动下载的模组 (CurseForge 独占或 Modrinth 上没有)
$manualMods = @(
    @{
        name = "Embeddium";
        version = "0.3.31+mc1.20.1";
        url = "https://www.curseforge.com/minecraft/mc-mods/embeddium/download";
        note = "需手动从 CurseForge 下载"
    },
    @{
        name = "Oculus";
        version = "mc1.20.1-1.8.0";
        url = "https://www.curseforge.com/minecraft/mc-mods/oculus/download";
        note = "需手动从 CurseForge 下载 (可选，Shader 支持)"
    },
    @{
        name = "FerriteCore";
        version = "6.0.1";
        url = "https://www.curseforge.com/minecraft/mc-mods/ferritecore/download";
        note = "需手动从 CurseForge 下载"
    },
    @{
        name = "ModernFix";
        version = "5.27.76+mc1.20.1";
        url = "https://www.curseforge.com/minecraft/mc-mods/modernfix/download";
        note = "需手动从 CurseForge 下载"
    },
    @{
        name = "Twilight Forest";
        version = "1.20.1-4.3.2124";
        url = "https://www.curseforge.com/minecraft/mc-mods/the-twilight-forest/download";
        note = "需手动从 CurseForge 下载 (魔法森林)"
    }
)

Write-Host "=== 从 Modrinth 下载模组 ===" -ForegroundColor Green
Write-Host ""

$successCount = 0
$failCount = 0

foreach ($mod in $modrinthMods) {
    $outputPath = Join-Path $OutputDir $mod.filename
    
    if (Test-Path $outputPath) {
        Write-Host "  [跳过] $($mod.name) ($($mod.version)) - 文件已存在" -ForegroundColor DarkGray
        $successCount++
        continue
    }
    
    Write-Host "  [下载] $($mod.name) ($($mod.version))..." -ForegroundColor White -NoNewline
    
    try {
        # 设置超时 30 秒
        $ProgressPreference = 'SilentlyContinue'
        Invoke-WebRequest -Uri $mod.url -OutFile $outputPath -TimeoutSec 60 -ErrorAction Stop
        $fileSize = (Get-Item $outputPath).Length / 1KB
        Write-Host " 完成 ($([math]::Round($fileSize, 1)) KB)" -ForegroundColor Green
        $successCount++
    }
    catch {
        Write-Host " 失败" -ForegroundColor Red
        Write-Host "         URL: $($mod.url)" -ForegroundColor DarkYellow
        Write-Host "         错误: $($_.Exception.Message)" -ForegroundColor DarkYellow
        $failCount++
    }
}

Write-Host ""
Write-Host "=== 需要手动下载的模组 ===" -ForegroundColor Yellow
Write-Host ""

foreach ($mod in $manualMods) {
    Write-Host "  $($mod.name) ($($mod.version))" -ForegroundColor White
    Write-Host "    URL: $($mod.url)" -ForegroundColor Cyan
    Write-Host "    $($mod.note)" -ForegroundColor DarkGray
    Write-Host ""
}

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "下载完成统计:" -ForegroundColor Cyan
Write-Host "  成功/跳过: $successCount 个" -ForegroundColor Green
Write-Host "  失败: $failCount 个" -ForegroundColor Red
Write-Host "  需手动下载: $($manualMods.Count) 个" -ForegroundColor Yellow
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "提示:" -ForegroundColor Yellow
Write-Host "1. 下载的模组已保存到: $((Resolve-Path $OutputDir).Path)" -ForegroundColor White
Write-Host "2. 将这些 .jar 文件复制到 PCL2 实例的 mods 文件夹" -ForegroundColor White
Write-Host "3. 或复制到 overrides/mods/ 后重新打包整合包" -ForegroundColor White
Write-Host ""
Write-Host "按任意键退出..."
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")