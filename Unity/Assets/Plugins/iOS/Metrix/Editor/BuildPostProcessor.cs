using UnityEngine;
using UnityEditor;
using UnityEditor.Callbacks;
using UnityEditor.iOS.Xcode;
using UnityEditor.iOS.Xcode.Extensions;

public class BuildPostProcessor
{
    [PostProcessBuildAttribute(1000)]
    public static void OnPostProcessBuild(BuildTarget target, string pathToBuildProject)
    {
        if (target == BuildTarget.iOS)
        {
            // Get project into C#
            var projectPath = PBXProject.GetPBXProjectPath(pathToBuildProject);
            var project = new PBXProject();
            project.ReadFromFile(projectPath);

            // Get targetGUID
#if UNITY_2019_3_OR_NEWER
            var targetGUID = pbxProject.GetUnityMainTargetGuid();
#else
            var targetName = PBXProject.GetUnityTargetName();
            var targetGUID = pbxProject.TargetGuidByName(targetName);
#endif             
            
            //Embed Metrix Framework
            const string metrixFramework = "Plugins/iOS/Metrix/Metrix.framework";
            string metrixGUID = project.AddFile(metrixFramework, "Frameworks/" + metrixFramework, PBXSourceTree.Sdk);
            PBXProjectExtensions.AddFileToEmbedFrameworks(project, targetGUID, metrixGUID);
            
            project.AddBuildProperty(targetGUID, "ALWAYS_EMBED_SWIFT_STANDARD_LIBRARIES", "YES");

            // Overwrite
            project.WriteToFile(projectPath);

            // Disable Bitcode

            // project.SetBuildProperty(targetGuid, "ENABLE_BITCODE", "NO");
            // project.WriteToFile (projectPath);
            
            // var projectInString = File.ReadAllText(projectPath);

            // projectInString = projectInString.Replace("ENABLE_BITCODE = YES;",
            //     $"ENABLE_BITCODE = NO;");
            // File.WriteAllText(projectPath, projectInString);
        }
    }
}